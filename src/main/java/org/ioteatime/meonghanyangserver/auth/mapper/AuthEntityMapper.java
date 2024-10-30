package org.ioteatime.meonghanyangserver.auth.mapper;

import org.ioteatime.meonghanyangserver.user.domain.UserEntity;
import org.ioteatime.meonghanyangserver.user.dto.UserDto;

public class AuthEntityMapper {
    public static UserEntity of(UserDto userDto, String encodedPassword) {
        return UserEntity.builder()
                .nickname(userDto.getNickname())
                .email(userDto.getEmail())
                .password(encodedPassword)
                .build();
    }
}
