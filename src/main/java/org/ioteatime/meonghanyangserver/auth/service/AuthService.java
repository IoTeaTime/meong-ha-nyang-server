package org.ioteatime.meonghanyangserver.auth.service;

import lombok.RequiredArgsConstructor;
import org.ioteatime.meonghanyangserver.auth.dto.reponse.LoginResponse;
import org.ioteatime.meonghanyangserver.auth.dto.reponse.RefreshResponse;
import org.ioteatime.meonghanyangserver.auth.dto.request.LoginRequest;
import org.ioteatime.meonghanyangserver.auth.mapper.AuthEntityMapper;
import org.ioteatime.meonghanyangserver.auth.mapper.AuthResponseMapper;
import org.ioteatime.meonghanyangserver.clients.google.GoogleMailClient;
import org.ioteatime.meonghanyangserver.common.error.ErrorTypeCode;
import org.ioteatime.meonghanyangserver.common.exception.ApiException;
import org.ioteatime.meonghanyangserver.common.utils.JwtUtils;
import org.ioteatime.meonghanyangserver.group.repository.groupuser.GroupUserRepository;
import org.ioteatime.meonghanyangserver.redis.RefreshToken;
import org.ioteatime.meonghanyangserver.redis.RefreshTokenRepository;
import org.ioteatime.meonghanyangserver.user.domain.UserEntity;
import org.ioteatime.meonghanyangserver.user.dto.UserDto;
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
    private final RefreshTokenRepository refreshTokenRepository;
    private final GroupUserRepository groupUserRepository;

    public LoginResponse login(LoginRequest loginRequest) {
        UserEntity userEntity =
                userRepository
                        .findByEmail(loginRequest.getEmail())
                        .orElseThrow(
                                () -> new ApiException(ErrorTypeCode.BAD_REQUEST, "없는 회원입니다."));

        boolean passwordMatch =
                bCryptPasswordEncoder.matches(loginRequest.getPassword(), userEntity.getPassword());
        if (!passwordMatch) {
            throw new ApiException(ErrorTypeCode.BAD_REQUEST, "비밀번호가 틀렸습니다.");
        }

        String accessToken = jwtUtils.generateAccessToken(userEntity);
        String refreshToken = jwtUtils.generateRefreshToken(userEntity);

        if (accessToken.isEmpty() || refreshToken.isEmpty()) {
            throw new ApiException(ErrorTypeCode.SERVER_ERROR);
        }
        RefreshToken refreshTokenEntity = RefreshToken.builder().refreshToken(refreshToken).build();

        refreshTokenRepository.save(refreshTokenEntity);
        accessToken = jwtUtils.includeBearer(accessToken);
        refreshToken = jwtUtils.includeBearer(refreshToken);

        return AuthResponseMapper.from(userEntity.getId(), accessToken, refreshToken);
    }

    public UserSimpleResponse joinProcess(UserDto userDto) {
        String encodedPassword = bCryptPasswordEncoder.encode(userDto.getPassword());
        UserEntity user = userRepository.save(AuthEntityMapper.of(userDto, encodedPassword));

        return AuthResponseMapper.from(user.getId(), user.getEmail());
    }

    public void send(String email) {
        // TODO. Redis 적용 후 코드 발급 구현 필요
        googleMailClient.sendMail(email, "hello", "world");
    }

    public UserSimpleResponse verifyEmail(String email) {
        UserEntity userEntity =
                userRepository
                        .findByEmail(email)
                        .orElseThrow(() -> new ApiException(ErrorTypeCode.NULL_POINT));

        return AuthResponseMapper.from(userEntity.getId(), userEntity.getEmail());
    }

    public RefreshResponse reissueAccessToken(String authorizationHeader) {
        String refreshToken = jwtUtils.extractTokenFromHeader(authorizationHeader);

        Long userId = jwtUtils.getIdFromToken(refreshToken);
        UserEntity userEntity =
                userRepository
                        .findById(userId)
                        .orElseThrow(
                                () ->
                                        new ApiException(
                                                ErrorTypeCode.BAD_REQUEST, "유효하지 않은 사용자입니다."));

        if (!jwtUtils.validateToken(refreshToken, userEntity)) {
            throw new ApiException(ErrorTypeCode.BAD_REQUEST, "Refresh token이 만료되었거나 유효하지 않습니다.");
        }

        RefreshToken storedToken =
                refreshTokenRepository
                        .findByRefreshToken(refreshToken)
                        .orElseThrow(
                                () ->
                                        new ApiException(
                                                ErrorTypeCode.BAD_REQUEST,
                                                "유효하지 않은 Refresh token입니다."));

        if (!storedToken.getRefreshToken().equals(refreshToken)) {
            throw new ApiException(ErrorTypeCode.BAD_REQUEST, "토큰이 일치하지 않습니다.");
        }

        String newAccessToken = jwtUtils.generateAccessToken(userEntity);
        newAccessToken = jwtUtils.includeBearer(newAccessToken);

        return AuthResponseMapper.from(newAccessToken);
    }
}
