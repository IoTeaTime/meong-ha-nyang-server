package org.ioteatime.meonghanyangserver.group.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import org.ioteatime.meonghanyangserver.cctv.dto.response.CctvInfoResponse;

@Schema(description = "그룹 통합 정보 응답")
public record GroupTotalResponse(
        @NotNull Long groupId,
        @NotBlank String groupName,
        @NotNull LocalDateTime createdAt,
        List<GroupUserInfoResponse> groupUser,
        List<CctvInfoResponse> cctv) {}
