package org.ioteatime.meonghanyangserver.user.service;

import lombok.RequiredArgsConstructor;
import org.ioteatime.meonghanyangserver.common.error.ErrorTypeCode;
import org.ioteatime.meonghanyangserver.common.exception.ApiException;
import org.ioteatime.meonghanyangserver.user.domain.UserEntity;
import org.ioteatime.meonghanyangserver.user.dto.response.UserDetailResponse;
import org.ioteatime.meonghanyangserver.user.dto.response.UserSimpleResponse;
import org.ioteatime.meonghanyangserver.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserDetailResponse getUserDetail(Long userId) {
        UserEntity userEntity =
                userRepository
                        .findById(userId)
                        .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return UserDetailResponse.from(userEntity);
    }

    public UserSimpleResponse userProcess(String email) {
        UserEntity userEntity =
                userRepository
                        .findByEmail(email)
                        .orElseThrow(() -> new ApiException(ErrorTypeCode.NULL_POINT));
        UserSimpleResponse userSimpleResponse =
                new UserSimpleResponse(userEntity.getId(), userEntity.getEmail());
        return userSimpleResponse;
    }
}
