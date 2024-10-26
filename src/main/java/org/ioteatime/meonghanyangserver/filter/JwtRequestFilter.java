package org.ioteatime.meonghanyangserver.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ioteatime.meonghanyangserver.common.error.ErrorTypeCode;
import org.ioteatime.meonghanyangserver.common.exception.ApiException;
import org.ioteatime.meonghanyangserver.common.utils.JwtUtils;
import org.ioteatime.meonghanyangserver.user.domain.UserEntity;
import org.ioteatime.meonghanyangserver.user.dto.CustomUserDetail;
import org.ioteatime.meonghanyangserver.user.repository.UserRepository;
import org.springframework.http.HttpHeaders;
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
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String uri = request.getRequestURI();
        if (uri.contains("/open-api")
                || uri.contains("/swagger-ui")
                || uri.contains("/v3/api-docs")
                || uri.contains("/static")) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwtToken = null;
        String subject = null;
        // Authorization 요청 헤더 존재 여부를 확인하고, 헤더 정보를 추출
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        //    authorizationHeader의 값이 Bearer로 시작하는지 확인 후 추출
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwtToken = authorizationHeader.substring(7);
            subject = jwtUtils.getSubjectFromToken(jwtToken);
            log.debug("jwt : ", jwtToken);
        } else {
            log.error("Authorization 헤더 누락 또는 토큰 형식 오류");
            throw new ApiException(ErrorTypeCode.UNAUTHORIZED, "Authorization 헤더 누락 또는 토큰 형식 오류");
        }
        // 현재 로그인된 사용자의 username과 토큰에 포함된 username 비교
        if (subject != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserEntity entity =
                    userRepository
                            .findByEmail(subject)
                            .orElseThrow(() -> new ApiException(ErrorTypeCode.BAD_REQUEST));
            log.debug(entity.getEmail());
            if (jwtUtils.validateToken(jwtToken, entity)) {
                // SecurityContextHolder에 userdetail 정보 저장
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
    }
}
