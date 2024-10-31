package org.ioteatime.meonghanyangserver.user.mapper;

import org.ioteatime.meonghanyangserver.user.domain.UserEntity;
import org.ioteatime.meonghanyangserver.user.dto.response.UserDetailResponse;

public class UserResponseMapper {
    public static UserDetailResponse from(UserEntity user) {
        return new UserDetailResponse(
                user.getId(), user.getEmail(), user.getProfileImgUrl(), user.getNickname());
    }
}
