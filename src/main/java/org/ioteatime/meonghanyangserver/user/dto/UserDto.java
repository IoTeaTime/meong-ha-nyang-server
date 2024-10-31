package org.ioteatime.meonghanyangserver.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDto {
    @Email
    @NotBlank
    @Schema(description = "이메일", example = "test@gmail.com")
    private String email;
    @NotBlank
    @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하여야 합니다.")
    private String password;
    @NotBlank
    @Schema(description = "닉네임", example = "멍하냥01")
    private String nickname;
    @Schema(description = "프로필 이미지 URL", example = "https://domain.com/abcd.png")
    private String profileImgUrl;
}
