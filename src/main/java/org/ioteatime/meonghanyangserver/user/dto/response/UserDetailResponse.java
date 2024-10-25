package org.ioteatime.meonghanyangserver.user.dto.response;

import java.util.Optional;
import org.ioteatime.meonghanyangserver.user.domain.UserEntity;

public record UserDetailResponse(Long id, String email, String profileImgUrl, String nickname) {
    public static UserDetailResponse from(Optional<UserEntity> optionalUser) {
        UserEntity user =
                optionalUser.orElseThrow(() -> new IllegalArgumentException("User not found"));

        return new UserDetailResponse(
                user.getId(), user.getNickname(), user.getEmail(), user.getProfileImgUrl());
    }
}
