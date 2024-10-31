package org.ioteatime.meonghanyangserver.auth.service;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;
import lombok.RequiredArgsConstructor;
import org.ioteatime.meonghanyangserver.auth.dto.reponse.LoginResponse;
import org.ioteatime.meonghanyangserver.auth.dto.reponse.RefreshResponse;
import org.ioteatime.meonghanyangserver.auth.dto.request.LoginRequest;
import org.ioteatime.meonghanyangserver.auth.mapper.AuthEntityMapper;
import org.ioteatime.meonghanyangserver.auth.mapper.AuthResponseMapper;
import org.ioteatime.meonghanyangserver.clients.google.GoogleMailClient;
import org.ioteatime.meonghanyangserver.common.exception.BadRequestException;
import org.ioteatime.meonghanyangserver.common.exception.NotFoundException;
import org.ioteatime.meonghanyangserver.common.exception.UnauthorizedException;
import org.ioteatime.meonghanyangserver.common.type.AuthErrorType;
import org.ioteatime.meonghanyangserver.common.utils.JwtUtils;
import org.ioteatime.meonghanyangserver.group.repository.groupuser.GroupUserRepository;
import org.ioteatime.meonghanyangserver.redis.EmailCode;
import org.ioteatime.meonghanyangserver.redis.EmailCodeRepository;
import org.ioteatime.meonghanyangserver.redis.RefreshToken;
import org.ioteatime.meonghanyangserver.redis.RefreshTokenRepository;
import org.ioteatime.meonghanyangserver.user.domain.UserEntity;
import org.ioteatime.meonghanyangserver.user.dto.request.JoinRequest;
import org.ioteatime.meonghanyangserver.user.dto.response.UserSimpleResponse;
import org.ioteatime.meonghanyangserver.user.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final GoogleMailClient googleMailClient;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtUtils jwtUtils;
    private final EmailCodeRepository emailCodeRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final GroupUserRepository groupUserRepository;

    public LoginResponse login(LoginRequest loginRequest) {
        UserEntity userEntity =
                userRepository
                        .findByEmail(loginRequest.email())
                        .orElseThrow(() -> new NotFoundException(AuthErrorType.NOT_FOUND));

        boolean passwordMatch =
                bCryptPasswordEncoder.matches(loginRequest.password(), userEntity.getPassword());
        if (!passwordMatch) {
            throw new BadRequestException(AuthErrorType.PASSWORD_NOT_MATCH);
        }

        String accessToken = jwtUtils.generateAccessToken(userEntity);
        String refreshToken = jwtUtils.generateRefreshToken(userEntity);

        if (accessToken.isEmpty() || refreshToken.isEmpty()) {
            throw new NotFoundException(AuthErrorType.TOKEN_NOT_FOUND);
        }
        RefreshToken refreshTokenEntity = RefreshToken.builder().refreshToken(refreshToken).build();

        refreshTokenRepository.save(refreshTokenEntity);
        accessToken = jwtUtils.includeBearer(accessToken);
        refreshToken = jwtUtils.includeBearer(refreshToken);

        return AuthResponseMapper.from(userEntity.getId(), accessToken, refreshToken);
    }

    public UserSimpleResponse joinProcess(JoinRequest userDto) {
        String encodedPassword = bCryptPasswordEncoder.encode(userDto.getPassword());
        UserEntity user = userRepository.save(AuthEntityMapper.of(userDto, encodedPassword));

        return AuthResponseMapper.from(user.getId(), user.getEmail());
    }

    public void send(String email) {
        String code = getCode();
        emailCodeRepository.save(EmailCode.builder().email(email).code(code).build());
        String mailSubject = "[\uD83D\uDC36 멍하냥] 이메일 인증 코드입니다.";
        String mailContent =
                """
                <h3>환영해요!</h3>
                <b>인증코드를 입력하세요</b>
                <p>%s</p>
                """
                        .formatted(code);
        googleMailClient.sendMail(email, mailSubject, mailContent);
    }

    private static String getCode() {
        ThreadLocalRandom random = ThreadLocalRandom.current();

        List<String> authStr = new CopyOnWriteArrayList<>();
        for (int i = 0; i < 3; i++) {
            authStr.add(String.valueOf(random.nextInt(10)));
        }
        for (int i = 0; i < 3; i++) {
            authStr.add(String.valueOf((char) (random.nextInt(26) + 65)));
        }

        Collections.shuffle(authStr);
        return String.join("", authStr);
    }

    public UserSimpleResponse verifyEmail(String email) {
        UserEntity userEntity =
                userRepository
                        .findByEmail(email)
                        .orElseThrow(() -> new NotFoundException(AuthErrorType.NOT_FOUND));

        return AuthResponseMapper.from(userEntity.getId(), userEntity.getEmail());
    }

    public void verifyEmailCode(String email, String code) {
        EmailCode emailCode =
                emailCodeRepository
                        .findByEmail(email)
                        .orElseThrow(() -> new NotFoundException(AuthErrorType.NOT_FOUND));
        if (!code.equals(emailCode.getCode())) {
            throw new UnauthorizedException(AuthErrorType.CODE_NOT_EQUALS);
        }
    }

    public RefreshResponse reissueAccessToken(String authorizationHeader) {
        String refreshToken = jwtUtils.extractTokenFromHeader(authorizationHeader);

        Long userId = jwtUtils.getIdFromToken(refreshToken);
        UserEntity userEntity =
                userRepository
                        .findById(userId)
                        .orElseThrow(() -> new NotFoundException(AuthErrorType.NOT_FOUND));

        if (!jwtUtils.validateToken(refreshToken, userEntity)) {
            throw new NotFoundException(AuthErrorType.REFRESH_TOKEN_INVALID);
        }

        RefreshToken storedToken =
                refreshTokenRepository
                        .findByRefreshToken(refreshToken)
                        .orElseThrow(
                                () -> new NotFoundException(AuthErrorType.REFRESH_TOKEN_INVALID));

        if (!storedToken.getRefreshToken().equals(refreshToken)) {
            throw new BadRequestException(AuthErrorType.TOKEN_NOT_EQUALS);
        }

        String newAccessToken = jwtUtils.generateAccessToken(userEntity);
        newAccessToken = jwtUtils.includeBearer(newAccessToken);

        return AuthResponseMapper.from(newAccessToken);
    }
}
