package org.ioteatime.meonghanyangserver.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class JoinRequest {

    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "이메일 형식으로 입력해주세요.")
    @Schema(description = "사용자의 이메일", example = "example@gmail.com")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Schema(description = "사용자의 비밀번호", example = "testpassword")
    @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하여야 합니다.")
    private String password;

    @NotBlank(message = "비밀번호를 확인해주세요.")
    @Schema(description = "비밀번호 확인", example = "testpassword")
    @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하여야 합니다.")
    private String passwordConfirm;

    @NotBlank(message = "닉네임을 입력해주세요.")
    @Schema(description = "사용자의 닉네임", example = "멍하냥01")
    private String nickname;

    @NotBlank
    @Schema(description = "Cognito 사용자 Id", example = "341234dc-2421-1235-943b-343a27cf923")
    private String cognitoId;

    @Builder
    public JoinRequest(String email, String password, String passwordConfirm, String nickname) {
        this.email = email;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
        this.nickname = nickname;
    }
}
