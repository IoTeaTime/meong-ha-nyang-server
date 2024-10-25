package org.ioteatime.meonghanyangserver.user.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.ioteatime.meonghanyangserver.user.domain.UserEntity;
import org.ioteatime.meonghanyangserver.user.dto.response.UserDetailResponse;
import org.ioteatime.meonghanyangserver.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserDetailResponse getUserDetail(Long userId) {
        Optional<UserEntity> userEntity = userRepository.findById(userId);
        return UserDetailResponse.from(userEntity);
    }
}
