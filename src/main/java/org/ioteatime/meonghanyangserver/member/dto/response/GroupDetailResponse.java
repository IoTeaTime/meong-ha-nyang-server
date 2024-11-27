package org.ioteatime.meonghanyangserver.member.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import org.ioteatime.meonghanyangserver.group.domain.GroupEntity;

@Schema(description = "그룹의 상세 정보 조회")
public record GroupDetailResponse(
        @NotNull @Schema(description = "그룹 ID", example = "1") Long id,
        @NotNull @Schema(description = "그룹 이름", example = "멍하냥 그룹") String groupName) {
    public static GroupDetailResponse from(GroupEntity group) {
        return new GroupDetailResponse(group.getId(), group.getGroupName());
    }
}
