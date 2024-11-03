package org.ioteatime.meonghanyangserver.auth.mapper;

import org.ioteatime.meonghanyangserver.auth.dto.reponse.LoginResponse;
import org.ioteatime.meonghanyangserver.auth.dto.reponse.RefreshResponse;
import org.ioteatime.meonghanyangserver.member.dto.response.MemberSimpleResponse;

public class AuthResponseMapper {
    public static MemberSimpleResponse from(Long id, String email) {
        return new MemberSimpleResponse(id, email);
    }

    public static LoginResponse from(Long id, String accessToken, String refreshToken) {
        return LoginResponse.builder()
                .memberId(id)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public static RefreshResponse from(String newAccessToken) {
        return new RefreshResponse(newAccessToken);
    }
}
