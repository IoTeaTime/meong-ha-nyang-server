package org.ioteatime.meonghanyangserver.auth.service;

import lombok.RequiredArgsConstructor;
import org.ioteatime.meonghanyangserver.auth.dto.request.EmailCheckRequest;
import org.ioteatime.meonghanyangserver.clients.google.GoogleMailClient;
import org.ioteatime.meonghanyangserver.common.error.ErrorTypeCode;
import org.ioteatime.meonghanyangserver.common.exception.ApiException;
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

    public UserSimpleResponse joinProcess(UserDto userDto) {
        String encodedPassword = bCryptPasswordEncoder.encode(userDto.getPassword());

        UserEntity userEntity =
                UserEntity.builder()
                        .nickname(userDto.getNickname())
                        .email(userDto.getEmail())
                        .password(encodedPassword)
                        .build();

        userRepository.save(userEntity);

        // 회원가입 응답 생성
        return new UserSimpleResponse(userEntity.getId(), userEntity.getEmail());
    }

    public void send(String email) {
        // TODO. Redis 적용 후 코드 발급 구현 필요
        googleMailClient.sendMail(email, "hello", "world");
    }

    public UserSimpleResponse verifyEmail(EmailCheckRequest emailCheckRequest) {
        UserEntity userEntity =
                userRepository
                        .findByEmail(emailCheckRequest.getEmail())
                        .orElseThrow(() -> new ApiException(ErrorTypeCode.NULL_POINT));
        return new UserSimpleResponse(userEntity.getId(), userEntity.getEmail());
    }
}
