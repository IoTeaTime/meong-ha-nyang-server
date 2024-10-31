package org.ioteatime.meonghanyangserver.user.service;

import lombok.RequiredArgsConstructor;
import org.ioteatime.meonghanyangserver.auth.dto.reponse.RefreshResponse;
import org.ioteatime.meonghanyangserver.auth.mapper.AuthResponseMapper;
import org.ioteatime.meonghanyangserver.common.exception.BadRequestException;
import org.ioteatime.meonghanyangserver.common.exception.NotFoundException;
import org.ioteatime.meonghanyangserver.common.type.AuthErrorType;
import org.ioteatime.meonghanyangserver.common.utils.JwtUtils;
import org.ioteatime.meonghanyangserver.redis.RefreshToken;
import org.ioteatime.meonghanyangserver.redis.RefreshTokenRepository;
import org.ioteatime.meonghanyangserver.user.domain.UserEntity;
import org.ioteatime.meonghanyangserver.user.dto.request.ChangePasswordRequest;
import org.ioteatime.meonghanyangserver.user.dto.response.UserDetailResponse;
import org.ioteatime.meonghanyangserver.user.mapper.UserResponseMapper;
import org.ioteatime.meonghanyangserver.user.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RefreshTokenRepository refreshTokenRepository;

    public UserDetailResponse getUserDetail(Long userId) {
        UserEntity userEntity =
                userRepository
                        .findById(userId)
                        .orElseThrow(() -> new NotFoundException(AuthErrorType.NOT_FOUND));

        return UserResponseMapper.from(userEntity);
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Transactional
    public void changeUserPassword(Long userId, ChangePasswordRequest request) {
        String currentPassword = request.currentPassword();
        String newPassword = request.newPassword();

        UserEntity userEntity =
                userRepository
                        .findById(userId)
                        .orElseThrow(() -> new NotFoundException(AuthErrorType.NOT_FOUND));

        if (!bCryptPasswordEncoder.matches(currentPassword, userEntity.getPassword())) {
            throw new BadRequestException(AuthErrorType.PASSWORD_NOT_MATCH);
        }

        // Dirty-Checking Password Change
        userEntity.setPassword(bCryptPasswordEncoder.encode(newPassword));
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
                                () -> new NotFoundException(AuthErrorType.REFRESH_TOKEN_NOT_FOUND));

        if (!storedToken.getRefreshToken().equals(refreshToken)) {
            throw new BadRequestException(AuthErrorType.TOKEN_NOT_EQUALS);
        }

        String newAccessToken = jwtUtils.generateAccessToken(userEntity);
        newAccessToken = jwtUtils.includeBearer(newAccessToken);

        return AuthResponseMapper.from(newAccessToken);
    }
}
