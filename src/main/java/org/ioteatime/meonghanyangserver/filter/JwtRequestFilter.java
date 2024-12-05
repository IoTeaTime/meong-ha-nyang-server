package org.ioteatime.meonghanyangserver.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ioteatime.meonghanyangserver.cctv.domain.CctvEntity;
import org.ioteatime.meonghanyangserver.cctv.repository.CctvRepository;
import org.ioteatime.meonghanyangserver.common.constant.JwtTokenType;
import org.ioteatime.meonghanyangserver.common.exception.*;
import org.ioteatime.meonghanyangserver.common.type.AuthErrorType;
import org.ioteatime.meonghanyangserver.common.type.CctvErrorType;
import org.ioteatime.meonghanyangserver.common.utils.JwtCctvUtils;
import org.ioteatime.meonghanyangserver.common.utils.JwtUtils;
import org.ioteatime.meonghanyangserver.member.domain.MemberEntity;
import org.ioteatime.meonghanyangserver.member.dto.CustomCctvDetail;
import org.ioteatime.meonghanyangserver.member.dto.CustomUserDetail;
import org.ioteatime.meonghanyangserver.member.repository.MemberRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {
    private final JwtUtils jwtUtils;
    private final JwtCctvUtils jwtCctvUtils;
    private final CctvRepository cctvRepository;
    private final MemberRepository memberRepository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String uri = request.getRequestURI();
        if (uri.contains("/open-api")
                || uri.contains("/swagger-ui")
                || uri.contains("/v3/api-docs")
                || uri.contains("/static")
                || (uri.contains("/api/cctv-device")
                        && request.getMethod().equalsIgnoreCase(HttpMethod.POST.name()))) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwtToken = null;
        Long jwtId = null;

        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            log.error("Authorization 헤더 누락 또는 토큰 형식 오류");
            throw new UnauthorizedException(AuthErrorType.HEADER_INVALID);
        }

        jwtToken = authorizationHeader.substring(7);
        // access token 블랙리스트 확인
        if (jwtUtils.blackListAccessToken(jwtToken)) {
            throw new UnauthorizedException(AuthErrorType.HEADER_INVALID);
        }

        if (jwtUtils.findTokenType(jwtToken) == JwtTokenType.CCTV_JWT) {
            // CCTV JWT인 경우 경로 확인
            validateCctvRequestPath(JwtTokenType.CCTV_JWT, uri);

            jwtId = jwtCctvUtils.getCctvIdFromToken(jwtToken);
            log.debug("jwt : ", jwtToken);

            if (jwtId == null || SecurityContextHolder.getContext().getAuthentication() != null) {
                return;
            }

            CctvEntity entity =
                    cctvRepository
                            .findById(jwtId)
                            .orElseThrow(() -> new NotFoundException(CctvErrorType.NOT_FOUND));

            log.debug(entity.getCctvNickname());
            if (!jwtCctvUtils.validateToken(jwtToken, entity)) {
                SecurityContextHolder.getContext().setAuthentication(null);
                return;
            }

            CustomCctvDetail customCctvDetail = new CustomCctvDetail(entity);
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(
                            customCctvDetail, null, customCctvDetail.getAuthorities());
            usernamePasswordAuthenticationToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext()
                    .setAuthentication(usernamePasswordAuthenticationToken);
        } else if (jwtUtils.findTokenType(jwtToken) == JwtTokenType.MEMBER_JWT) {
            validateCctvRequestPath(JwtTokenType.MEMBER_JWT, uri);

            jwtId = jwtUtils.getIdFromToken(jwtToken);
            log.debug("jwt : ", jwtToken);

            if (jwtId == null || SecurityContextHolder.getContext().getAuthentication() != null) {
                return;
            }

            MemberEntity entity =
                    memberRepository
                            .findById(jwtId)
                            .orElseThrow(() -> new NotFoundException(AuthErrorType.NOT_FOUND));

            log.debug(entity.getEmail());
            if (!jwtUtils.validateToken(jwtToken, entity)) {
                SecurityContextHolder.getContext().setAuthentication(null);
                return;
            }

            CustomUserDetail customUserDetail = new CustomUserDetail(entity);
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(
                            customUserDetail, null, customUserDetail.getAuthorities());
            usernamePasswordAuthenticationToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext()
                    .setAuthentication(usernamePasswordAuthenticationToken);
        }

        filterChain.doFilter(request, response);
    }

    private void validateCctvRequestPath(JwtTokenType jwtTokenType, String uri) {
        // CCTV 요청 경로가 올바르지 않으면 에러
        boolean isValidPath =
                uri.contains("/api/video-device")
                        || uri.contains("/api/cctv-device")
                        || uri.contains("/api/image-device");
        if (jwtTokenType == JwtTokenType.CCTV_JWT) {
            if (isValidPath) {
                return;
            }
            throw new BadRequestException(CctvErrorType.INVALID_CCTV_JWT_REQUEST);
        } else if (jwtTokenType == JwtTokenType.MEMBER_JWT) {
            if (isValidPath) {
                throw new BadRequestException(AuthErrorType.INVALID_AUTH_JWT_REQUEST);
            }
        }
    }
}
