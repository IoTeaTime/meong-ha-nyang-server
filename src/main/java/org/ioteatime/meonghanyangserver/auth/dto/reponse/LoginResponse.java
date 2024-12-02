package org.ioteatime.meonghanyangserver.auth.dto.reponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.ioteatime.meonghanyangserver.groupmember.doamin.enums.GroupMemberRole;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record LoginResponse(
        @NotNull @Schema(description = "회원 ID", example = "1") Long memberId,
        @NotBlank @Schema(description = "회원 AccessToken", example = "Bearer abcd...")
                String accessToken,
        @NotBlank @Schema(description = "회원 RefreshToken", example = "Bearer abcd...")
                String refreshToken,
        @NotNull @Schema(description = "회원이 가입한 그룹이 있는지 여부", example = "true")
                Boolean isGroupMember,
        @Schema(description = "회원이 가입한 그룹의 ID", example = "1") Long groupId,
        @Schema(description = "회원의 그룹 역할", example = "MASTER") GroupMemberRole role) {

    @Builder
    public LoginResponse(
            Long memberId,
            String accessToken,
            String refreshToken,
            Boolean isGroupMember,
            Long groupId,
            GroupMemberRole role) {
        this.memberId = memberId;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.isGroupMember = isGroupMember;
        this.groupId = groupId;
        this.role = role;
    }
}
