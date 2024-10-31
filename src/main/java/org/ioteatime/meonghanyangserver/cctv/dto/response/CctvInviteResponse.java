package org.ioteatime.meonghanyangserver.cctv.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "CCTV 초대 응답")
public record CctvInviteResponse(
        @Schema(description = "그룹 ID", example = "1") Long groupId,
        @NotNull
                @Schema(
                        description = "KVS 채널 이름",
                        example = "kvs-9bc02f62-df22-40b0-bdc1-610d8fd293b4")
                String kvsChannelId) {}
