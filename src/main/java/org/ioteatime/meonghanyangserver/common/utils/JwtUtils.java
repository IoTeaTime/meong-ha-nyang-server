package org.ioteatime.meonghanyangserver.common.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.security.Key;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;
import javax.crypto.spec.SecretKeySpec;
import lombok.extern.slf4j.Slf4j;
import org.ioteatime.meonghanyangserver.user.domain.UserEntity;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtUtils {
    private Key hmacKey;

    public JwtUtils(Environment env) {
        this.hmacKey =
                hmacKey =
                        new SecretKeySpec(
                                Base64.getDecoder().decode(env.getProperty("token.secret")),
                                SignatureAlgorithm.HS256.getJcaName());
    }

    public String generateAccessToken(UserEntity userEntity) {
        Date nowDate = new Date();
        Date expiration = new Date(nowDate.getTime() + Duration.ofHours(2).toMillis());
        String jwtToken =
                Jwts.builder()
                        .claim("name", userEntity.getNickname())
                        .claim("sub", userEntity.getEmail())
                        .claim("jti", String.valueOf(userEntity.getId()))
                        .claim("role", "ROLE_USER")
                        .claim("iat", nowDate)
                        .claim("exp", expiration)
                        .signWith(hmacKey)
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
                        .claim("sub", userEntity.getEmail())
                        .claim("jti", String.valueOf(userEntity.getId()))
                        .claim("role", "ROLE_USER")
                        .claim("iat", nowDate)
                        .claim("exp", expiration)
                        .signWith(hmacKey)
                        .compact();
        log.debug(jwtToken);
        return jwtToken;
    }

    private Claims getAllClaimsFromToken(String token) {
        Jws<Claims> jwt = Jwts.parser().setSigningKey(hmacKey).build().parseClaimsJws(token);
        return jwt.getBody();
    }

    public String getSubjectFromToken(String token) {
        final Claims claims = getAllClaimsFromToken(token);
        return claims.getSubject();
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
        // 토큰 유효기간 체크
        if (isTokenExpired(token)) {
            return false;
        }

        // 토큰 내용을 검증
        String subject = getSubjectFromToken(token);
        String email = userEntity.getEmail();

        return subject != null && email != null && subject.equals(email);
    }

    public String extractTokenFromHeader(String authorizationHeader) {
        return authorizationHeader.replace("Bearer ", "");
    }

    public Long getIdFromToken(String token) {
        final Claims claims = getAllClaimsFromToken(token);
        return Long.valueOf(String.valueOf(claims.get("jti")));
    }
}
