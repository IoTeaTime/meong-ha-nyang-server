package org.ioteatime.meonghanyangserver.auth.mapper;

import org.ioteatime.meonghanyangserver.user.domain.UserEntity;
import org.ioteatime.meonghanyangserver.user.dto.request.JoinRequest;

public class AuthEntityMapper {
    public static UserEntity of(JoinRequest userDto, String encodedPassword) {
        return UserEntity.builder()
                .nickname(userDto.getNickname())
                .email(userDto.getEmail())
                .password(encodedPassword)
                .build();
    }
}
