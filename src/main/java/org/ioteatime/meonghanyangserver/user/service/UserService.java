package org.ioteatime.meonghanyangserver.user.service;

import lombok.RequiredArgsConstructor;
import org.ioteatime.meonghanyangserver.common.error.ErrorTypeCode;
import org.ioteatime.meonghanyangserver.common.exception.ApiException;
import org.ioteatime.meonghanyangserver.user.domain.UserEntity;
import org.ioteatime.meonghanyangserver.user.dto.CustomUserDetail;
import org.ioteatime.meonghanyangserver.user.dto.request.ChangePasswordRequest;
import org.ioteatime.meonghanyangserver.user.dto.response.UserDetailResponse;
import org.ioteatime.meonghanyangserver.user.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserDetailResponse getUserDetail(Long userId) {
        UserEntity userEntity =
                userRepository
                        .findById(userId)
                        .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return UserDetailResponse.from(userEntity);
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Transactional
    public void changeUserPassword(Authentication authentication, ChangePasswordRequest request) {
        String currentPassword = request.getCurrentPassword();
        String newPassword = request.getNewPassword();
        CustomUserDetail userDetails = (CustomUserDetail) authentication.getPrincipal();
        Long userId = userDetails.getUserEntity().getId();

        UserEntity userEntity =
                userRepository
                        .findById(userId)
                        .orElseThrow(
                                () ->
                                        new ApiException(
                                                ErrorTypeCode.BAD_REQUEST, "User not found"));

        if (!bCryptPasswordEncoder.matches(currentPassword, userEntity.getPassword())) {
            throw new ApiException(ErrorTypeCode.BAD_REQUEST, "현재 비밀번호가 일치하지 않습니다.");
        }

        // Dirty-Checking Password Change
        userEntity.setPassword(bCryptPasswordEncoder.encode(newPassword));
    }
}
