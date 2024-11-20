package org.ioteatime.meonghanyangserver.cctv.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "cctv 정보 응답")
public record CctvInfoResponse(
        @NotBlank @Schema(description = "CCTV ID", example = "1") Long cctvId,
        @NotBlank @Schema(description = "CCTV 닉네임", example = "거실") String cctvNickname,
        @NotBlank @Schema(description = "기기의 SSAID", example = "9774d56d682e549c") String thingId,
        @NotBlank @Schema(description = "kvs 채널 이름", example = "channelname")
                String kvsChannelName) {}
