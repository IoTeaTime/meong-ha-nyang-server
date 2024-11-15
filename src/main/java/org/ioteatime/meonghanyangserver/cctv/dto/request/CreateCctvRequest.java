package org.ioteatime.meonghanyangserver.cctv.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateCctvRequest(
        @NotNull @Schema(description = "그룹 ID", example = "1") Long groupId,
        @NotBlank @Schema(description = "Thing ID", example = "cf27414r3b22c023") String thingId,
        @NotBlank
                @Schema(
                        description = "Kvs Channel 이름",
                        example = "kvs-9bc02f62-df22-40b0-bdc1-610d8fd293b4")
                String kvsChannelName) {}
