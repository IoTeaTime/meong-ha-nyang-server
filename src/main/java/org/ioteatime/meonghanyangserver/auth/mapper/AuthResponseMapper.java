package org.ioteatime.meonghanyangserver.auth.mapper;

import org.ioteatime.meonghanyangserver.auth.dto.db.LoginWithMemberInfo;
import org.ioteatime.meonghanyangserver.auth.dto.reponse.LoginResponse;
import org.ioteatime.meonghanyangserver.auth.dto.reponse.RefreshResponse;
import org.ioteatime.meonghanyangserver.member.dto.response.MemberSimpleResponse;

public class AuthResponseMapper {
    public static MemberSimpleResponse from(Long id, String email) {
        return new MemberSimpleResponse(id, email);
    }

    public static LoginResponse from(
            LoginWithMemberInfo memberInfo, String accessToken, String refreshToken) {
        return LoginResponse.builder()
                .memberId(memberInfo.memberId())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .isGroupMember(memberInfo.isGroupMember())
                .role(memberInfo.role())
                .build();
    }

    public static RefreshResponse from(String newAccessToken) {
        return new RefreshResponse(newAccessToken);
    }
}
