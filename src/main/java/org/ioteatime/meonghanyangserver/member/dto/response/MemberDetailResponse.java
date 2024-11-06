package org.ioteatime.meonghanyangserver.member.dto.response;

import org.ioteatime.meonghanyangserver.member.domain.MemberEntity;

public record MemberDetailResponse(Long id, String email, String profileImgUrl, String nickname) {
    public static MemberDetailResponse from(MemberEntity member) {
        return new MemberDetailResponse(
                member.getId(), member.getEmail(), member.getProfileImgUrl(), member.getNickname());
    }
}
