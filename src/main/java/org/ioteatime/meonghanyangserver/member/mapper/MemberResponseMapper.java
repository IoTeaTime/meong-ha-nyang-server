package org.ioteatime.meonghanyangserver.member.mapper;

import org.ioteatime.meonghanyangserver.member.domain.MemberEntity;
import org.ioteatime.meonghanyangserver.member.dto.response.MemberDetailResponse;

public class MemberResponseMapper {
    public static MemberDetailResponse from(MemberEntity member) {
        return new MemberDetailResponse(
                member.getId(), member.getEmail(), member.getProfileImgUrl(), member.getNickname());
    }
}
