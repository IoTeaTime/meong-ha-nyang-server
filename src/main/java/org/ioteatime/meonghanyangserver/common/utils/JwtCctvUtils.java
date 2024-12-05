package org.ioteatime.meonghanyangserver.common.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Base64;
import java.util.Date;
import java.util.Objects;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;
import org.ioteatime.meonghanyangserver.cctv.domain.CctvEntity;
import org.ioteatime.meonghanyangserver.common.exception.UnauthorizedException;
import org.ioteatime.meonghanyangserver.common.type.CctvErrorType;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtCctvUtils {
    private final SecretKey cctvKey;

    public JwtCctvUtils(Environment env) {
        String cctvSecretKeyString = env.getProperty("token.cctv-secret");
        byte[] decodedCctvKey = Base64.getDecoder().decode(cctvSecretKeyString);
        this.cctvKey = Keys.hmacShaKeyFor(decodedCctvKey);
    }

    public String generateCctvAccessToken(String cctvName, Long cctvId) {
        Date nowDate = new Date();
        String jwtToken =
                Jwts.builder()
                        .claim("name", cctvName)
                        .claim("sub", "meong-ha-nyang")
                        .claim("jti", String.valueOf(cctvId))
                        .claim("iat", nowDate)
                        .claim("exp", null)
                        .signWith(cctvKey)
                        .compact();
        log.debug(jwtToken);
        return jwtToken;
    }

    private Claims getAllCctvClaimsFromToken(String token) {
        try {
            Jws<Claims> jwt =
                    Jwts.parser()
                            .verifyWith(cctvKey) // SecretKey를 이용한 서명 검증
                            .build()
                            .parseSignedClaims(token);
            return jwt.getPayload();
        } catch (ExpiredJwtException exception) {
            throw new UnauthorizedException(CctvErrorType.INVALID_TOKEN);
        }
    }

    public Long getCctvIdFromToken(String token) {
        final Claims claims = getAllCctvClaimsFromToken(token);
        return Long.valueOf(String.valueOf(claims.get("jti")));
    }

    public boolean validateToken(String token, CctvEntity cctvEntity) {
        Long jwtId = getCctvIdFromToken(token);
        Long id = cctvEntity.getId();

        return id != null && Objects.equals(jwtId, id);
    }
}
