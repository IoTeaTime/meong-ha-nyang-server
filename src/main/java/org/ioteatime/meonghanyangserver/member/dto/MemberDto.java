package org.ioteatime.meonghanyangserver.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MemberDto {
    @Email
    @NotBlank
    @Schema(description = "이메일", example = "test@gmail.com")
    private String email;

    @NotBlank
    @Schema(description = "Cognito 사용자 id", example = "341234dc-2421-1235-943b-343a27cf923")
    private String cognitoId;

    @NotBlank
    @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하여야 합니다.")
    private String password;

    @NotBlank
    @Schema(description = "닉네임", example = "멍하냥01")
    private String nickname;

    @Schema(description = "프로필 이미지 URL", example = "https://domain.com/abcd.png")
    private String profileImgUrl;
}
