package org.ioteatime.meonghanyangserver.user.dto;

import lombok.Getter;

@Getter
public class UserDto {
    private Long id;
    private String email;
    private String profileImgUrl;
    private String nickname;

    public UserDto(Long id, String email, String profileImgUrl, String nickname) {
        this.id = id;
        this.email = email;
        this.profileImgUrl = profileImgUrl;
        this.nickname = nickname;
    }
}
