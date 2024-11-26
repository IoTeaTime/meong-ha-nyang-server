package org.ioteatime.meonghanyangserver.group.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import org.ioteatime.meonghanyangserver.cctv.dto.response.CctvInfoResponse;

@Schema(description = "그룹 통합 정보 응답")
public record GroupTotalResponse(
        @NotNull @Schema(description = "그룹 ID", example = "1") Long groupId,
        @NotBlank @Schema(description = "그룹 이름", example = "멍하냥 그룹") String groupName,
        @NotNull @Schema(description = "그룹 생성 시간", example = "2024-10-10 15:00")
                LocalDateTime createdAt,
        @Schema(description = "그룹의 CCTV 정보 목록") List<CctvInfoResponse> cctv) {}
