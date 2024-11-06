package org.ioteatime.meonghanyangserver.auth.mapper;

import org.ioteatime.meonghanyangserver.member.domain.MemberEntity;
import org.ioteatime.meonghanyangserver.member.dto.request.JoinRequest;

public class AuthEntityMapper {
    public static MemberEntity of(JoinRequest userDto, String encodedPassword) {
        return MemberEntity.builder()
                .nickname(userDto.getNickname())
                .email(userDto.getEmail())
                .password(encodedPassword)
                .build();
    }
}
