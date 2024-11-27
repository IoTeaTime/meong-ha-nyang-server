package org.ioteatime.meonghanyangserver.member.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Schema(description = "회원 및 그룹 정보 조회")
public record MemberWithGroupDetailResponse(
        @NotNull @Schema(description = "회원 정보") MemberDetailResponse member,
        @Schema(description = "그룹 정보") GroupDetailResponse group) {
    @Builder
    public MemberWithGroupDetailResponse(MemberDetailResponse member, GroupDetailResponse group) {
        this.member = member;
        this.group = group;
    }
}
