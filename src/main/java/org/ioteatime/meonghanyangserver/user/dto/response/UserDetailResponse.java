package org.ioteatime.meonghanyangserver.user.dto.response;

import lombok.Getter;

@Getter
public class UserDetailResponse {
    private Long id;
    private String email;
    private String profileImgUrl;
    private String nickname;

    public UserDetailResponse(Long id, String email, String profileImgUrl, String nickname) {
        this.id = id;
        this.email = email;
        this.profileImgUrl = profileImgUrl;
        this.nickname = nickname;
    }
}
