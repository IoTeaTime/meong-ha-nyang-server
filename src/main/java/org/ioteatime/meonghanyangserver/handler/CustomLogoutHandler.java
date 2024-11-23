package org.ioteatime.meonghanyangserver.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ioteatime.meonghanyangserver.common.utils.JwtUtils;
import org.ioteatime.meonghanyangserver.redis.AccessToken;
import org.ioteatime.meonghanyangserver.redis.AccessTokenRepository;
import org.ioteatime.meonghanyangserver.redis.RefreshTokenRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomLogoutHandler implements LogoutHandler {
    private final JwtUtils jwtUtils;
    private final RefreshTokenRepository refreshTokenRepository;
    private final AccessTokenRepository accessTokenRepository;

    @Override
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        // token 없을 시
        if (authorizationHeader.isEmpty()) {
            return;
        }
        String accessToken = authorizationHeader.substring(7);

        // access token이 블랙리스트에 있을 경우
        if (accessTokenRepository.existsByAccessToken(accessToken)) {
            return;
        }

        Long memberId = jwtUtils.getIdFromToken(accessToken);
        log.debug("jwt : ", accessToken);
        refreshTokenRepository.deleteByMemberId(memberId);
        Date date = jwtUtils.getExpirationDateFromToken(accessToken);
        // access token의 남은 시간
        long ttl = (date.getTime() - System.currentTimeMillis()) / 1000;

        AccessToken accessTokenEntity =
                AccessToken.builder().accessToken(accessToken).ttl(ttl).build();
        // access token 블랙리스트
        accessTokenRepository.save(accessTokenEntity);
    }
}
