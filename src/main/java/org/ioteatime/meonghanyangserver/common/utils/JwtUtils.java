package org.ioteatime.meonghanyangserver.common.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;
import java.util.Objects;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;
import org.ioteatime.meonghanyangserver.user.domain.UserEntity;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtUtils {
    private final SecretKey key;

    public JwtUtils(Environment env) {
        String secretKeyString = env.getProperty("token.secret");
        byte[] decodedKey = Base64.getDecoder().decode(secretKeyString);
        this.key = Keys.hmacShaKeyFor(decodedKey);
    }

    public String generateAccessToken(UserEntity userEntity) {
        Date nowDate = new Date();
        Date expiration = new Date(nowDate.getTime() + Duration.ofHours(2).toMillis());
        String jwtToken =
                Jwts.builder()
                        .claim("name", userEntity.getNickname())
                        .claim("sub", "meong-ha-nyang")
                        .claim("jti", String.valueOf(userEntity.getId()))
                        .claim("iat", nowDate)
                        .claim("exp", expiration)
                        .signWith(key)
                        .compact();
        log.debug(jwtToken);
        return jwtToken;
    }

    public String generateRefreshToken(UserEntity userEntity) {
        Date nowDate = new Date();
        Date expiration = new Date(nowDate.getTime() + Duration.ofDays(30).toMillis());
        String jwtToken =
                Jwts.builder()
                        .claim("name", userEntity.getNickname())
                        .claim("sub", "meong-ha-nyang")
                        .claim("jti", String.valueOf(userEntity.getId()))
                        .claim("iat", nowDate)
                        .claim("exp", expiration)
                        .signWith(key)
                        .compact();
        log.debug(jwtToken);
        return jwtToken;
    }

    private Claims getAllClaimsFromToken(String token) {
        Jws<Claims> jwt =
                Jwts.parser()
                        .verifyWith(key) // SecretKey를 이용한 서명 검증
                        .build()
                        .parseSignedClaims(token);
        return jwt.getPayload();
    }

    public String getNameFromToken(String token) {
        final Claims claims = getAllClaimsFromToken(token);
        return String.valueOf(claims.get("name"));
    }

    private Date getExpirationDateFromToken(String token) {
        final Claims claims = getAllClaimsFromToken(token);
        return claims.getExpiration();
    }

    private boolean isTokenExpired(String token) {
        Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public boolean validateToken(String token, UserEntity userEntity) {
        if (isTokenExpired(token)) {
            return false;
        }

        Long jwtId = getIdFromToken(token);
        Long id = userEntity.getId();

        return id != null && Objects.equals(jwtId, id);
    }

    public String extractTokenFromHeader(String authorizationHeader) {
        return authorizationHeader.replace("Bearer ", "");
    }

    public Long getIdFromToken(String token) {
        final Claims claims = getAllClaimsFromToken(token);
        return Long.valueOf(String.valueOf(claims.get("jti")));
    }

    public String includeBearer(String token) {
        return "Bearer " + token;
    }
}
