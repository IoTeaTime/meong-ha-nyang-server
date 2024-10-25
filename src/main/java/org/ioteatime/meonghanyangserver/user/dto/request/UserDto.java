package org.ioteatime.meonghanyangserver.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {

    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "이메일 형식으로 입력해주세요.")
    @Schema(
            description = "사용자의 이메일",
            example = "test@test.com",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Schema(
            description = "사용자의 비밀번호",
            example = "testpassword",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;

    @NotBlank(message = "비밀번호를 확인해주세요.")
    @Schema(
            description = "비밀번호 확인",
            example = "testpassword",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String passwordConfirm;

    @NotBlank(message = "닉네임을 입력해주세요.")
    @Schema(
            description = "사용자의 닉네임",
            example = "mynickname",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String nickname;

    @Schema(description = "사용자의 프로필 이미지 URL", example = "http://example.com/example.jpg")
    private String profileImgUrl;

    @Builder
    public UserDto(
            String email,
            String password,
            String passwordConfirm,
            String nickname,
            String profileImgUrl) {
        this.email = email;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
        this.nickname = nickname;
        this.profileImgUrl = profileImgUrl;
    }
}
