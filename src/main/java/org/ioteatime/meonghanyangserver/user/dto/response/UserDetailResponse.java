package org.ioteatime.meonghanyangserver.user.dto.response;

import org.ioteatime.meonghanyangserver.user.domain.UserEntity;

public record UserDetailResponse(Long id, String email, String profileImgUrl, String nickname) {
    public static UserDetailResponse from(UserEntity user) {
        return new UserDetailResponse(
                user.getId(), user.getEmail(), user.getProfileImgUrl(), user.getNickname());
    }
}
