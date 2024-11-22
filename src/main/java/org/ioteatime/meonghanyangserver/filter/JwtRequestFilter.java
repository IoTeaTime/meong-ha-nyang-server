package org.ioteatime.meonghanyangserver.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ioteatime.meonghanyangserver.common.api.Api;
import org.ioteatime.meonghanyangserver.common.exception.*;
import org.ioteatime.meonghanyangserver.common.type.AuthErrorType;
import org.ioteatime.meonghanyangserver.common.type.CommonErrorType;
import org.ioteatime.meonghanyangserver.common.utils.JwtUtils;
import org.ioteatime.meonghanyangserver.groupmember.repository.GroupMemberRepository;
import org.ioteatime.meonghanyangserver.member.domain.MemberEntity;
import org.ioteatime.meonghanyangserver.member.dto.CustomUserDetail;
import org.ioteatime.meonghanyangserver.member.repository.MemberRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final MemberRepository memberRepository;
    private final GroupMemberRepository deviceRepository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String uri = request.getRequestURI();
            if (uri.contains("/open-api")
                    || uri.contains("/swagger-ui")
                    || uri.contains("/v3/api-docs")
                    || uri.contains("/static")) {
                filterChain.doFilter(request, response);
                return;
            }

            String jwtToken = null;
            Long jwtId = null;

            String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                jwtToken = authorizationHeader.substring(7);
                //access token 블랙리스트 확인
                if (jwtUtils.blackListAccessToken(jwtToken)) {
                    throw new UnauthorizedException(AuthErrorType.HEADER_INVALID);
                }
                jwtId = jwtUtils.getIdFromToken(jwtToken);
                log.debug("jwt : ", jwtToken);
            } else {
                log.error("Authorization 헤더 누락 또는 토큰 형식 오류");
                throw new UnauthorizedException(AuthErrorType.HEADER_INVALID);
            }
            if (jwtId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                MemberEntity entity =
                        memberRepository
                                .findById(jwtId)
                                .orElseThrow(() -> new NotFoundException(AuthErrorType.NOT_FOUND));

                log.debug(entity.getEmail());
                if (jwtUtils.validateToken(jwtToken, entity)) {
                    CustomUserDetail customUserDetail = new CustomUserDetail(entity);
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(
                                    customUserDetail, null, customUserDetail.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext()
                            .setAuthentication(usernamePasswordAuthenticationToken);
                } else {
                    SecurityContextHolder.getContext().setAuthentication(null);
                    return;
                }
                filterChain.doFilter(request, response);
            }
        }catch (ApiExceptionImpl exception) {
            exceptionHandle(response, exception);
        }
    }
    //jwtRequest filter exception handle
    private void exceptionHandle(HttpServletResponse response, ApiExceptionImpl exception) throws IOException {
        response.setStatus(exception.getHttpStatus().value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Api<Object> apiResponse = Api.fail(exception);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(apiResponse);

        response.getWriter().write(jsonResponse);
    }
}
