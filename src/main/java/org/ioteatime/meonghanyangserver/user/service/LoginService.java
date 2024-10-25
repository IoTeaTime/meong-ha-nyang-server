package org.ioteatime.meonghanyangserver.user.service;

import lombok.RequiredArgsConstructor;
import org.ioteatime.meonghanyangserver.common.error.ErrorTypeCode;
import org.ioteatime.meonghanyangserver.common.exception.ApiException;
import org.ioteatime.meonghanyangserver.common.utils.JwtUtils;
import org.ioteatime.meonghanyangserver.user.domain.UserEntity;
import org.ioteatime.meonghanyangserver.user.dto.request.LoginDto;
import org.ioteatime.meonghanyangserver.user.dto.response.LoginResponse;
import org.ioteatime.meonghanyangserver.user.repository.UserRepository;
import org.ioteatime.meonghanyangserver.user.repository.redis.RefreshToken;
import org.ioteatime.meonghanyangserver.user.repository.redis.RefreshTokenRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RefreshTokenRepository refreshTokenRepository;
    public LoginResponse login(LoginDto loginDto) {
        UserEntity userEntity = userRepository.findByEmail(loginDto.getEmail())
                .orElseThrow(()->new ApiException(ErrorTypeCode.BAD_REQUEST,"없는 회원입니다."));
        //바말번호 확인
        boolean passwordMatch = bCryptPasswordEncoder.matches(loginDto.getPassword(), userEntity.getPassword());
        if (!passwordMatch) {
            throw new ApiException(ErrorTypeCode.BAD_REQUEST, "비밀번호가 틀렸습니다.");
        }

        String accessToken = jwtUtils.generateAccessToken(userEntity);
        String refreshToken = jwtUtils.generateRefreshToken(userEntity);

        if(accessToken.isEmpty() || refreshToken.isEmpty()){
            throw new ApiException(ErrorTypeCode.SERVER_ERROR);
        }
        RefreshToken refreshTokenEntity = RefreshToken.builder()
                .refreshToken(refreshToken)
                .build();

        refreshTokenRepository.save(refreshTokenEntity);

        return LoginResponse.builder()
                .userId(userEntity.getId())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
