package org.ioteatime.meonghanyangserver.auth.mapper;

import org.ioteatime.meonghanyangserver.auth.dto.reponse.LoginResponse;
import org.ioteatime.meonghanyangserver.auth.dto.reponse.RefreshResponse;
import org.ioteatime.meonghanyangserver.user.dto.response.UserSimpleResponse;

public class AuthResponseMapper {
    public static UserSimpleResponse from(Long id, String email) {
        return new UserSimpleResponse(id, email);
    }

    public static LoginResponse from(Long id, String accessToken, String refreshToken) {
        return LoginResponse.builder()
                .userId(id)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public static RefreshResponse from(String newAccessToken) {
        return new RefreshResponse(newAccessToken);
    }
}
