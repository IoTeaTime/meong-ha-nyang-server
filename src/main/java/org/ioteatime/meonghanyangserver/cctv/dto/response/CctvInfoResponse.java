package org.ioteatime.meonghanyangserver.cctv.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "cctv 정보 응답")
public record CctvInfoResponse(
        @NotBlank Long cctvId,
        @NotBlank String cctvNickname,
        @NotBlank String thingId,
        @NotBlank String kvsChannelName) {}
