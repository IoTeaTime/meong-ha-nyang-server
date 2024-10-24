package org.ioteatime.meonghanyangserver.user.dto;

import lombok.Getter;

@Getter
public class UserDto {
    private Long id;
    private String email;
    private String profileImgUrl;
    private String password;
    private String nickname;
}
