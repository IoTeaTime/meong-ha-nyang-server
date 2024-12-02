package org.ioteatime.meonghanyangserver.auth.mapper;

import org.ioteatime.meonghanyangserver.auth.dto.reponse.LoginResponse;
import org.ioteatime.meonghanyangserver.auth.dto.reponse.RefreshResponse;
import org.ioteatime.meonghanyangserver.groupmember.doamin.GroupMemberEntity;
import org.ioteatime.meonghanyangserver.member.domain.MemberEntity;
import org.ioteatime.meonghanyangserver.member.dto.response.MemberSimpleResponse;

public class AuthResponseMapper {
    public static MemberSimpleResponse from(Long id, String email) {
        return new MemberSimpleResponse(id, email);
    }

    public static LoginResponse from(
            MemberEntity member,
            GroupMemberEntity groupMember,
            String accessToken,
            String refreshToken) {
        return LoginResponse.builder()
                .memberId(member.getId())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .isGroupMember(true)
                .groupId(groupMember.getGroup().getId())
                .role(groupMember.getRole())
                .build();
    }

    public static LoginResponse from(MemberEntity member, String accessToken, String refreshToken) {
        return LoginResponse.builder()
                .memberId(member.getId())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .isGroupMember(false)
                .build();
    }

    public static RefreshResponse from(String newAccessToken) {
        return new RefreshResponse(newAccessToken);
    }
}
