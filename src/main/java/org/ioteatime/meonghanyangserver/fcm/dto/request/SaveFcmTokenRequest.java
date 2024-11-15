package org.ioteatime.meonghanyangserver.fcm.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "FCM 토큰 저장 요청")
public record SaveFcmTokenRequest(
        @NotNull @Schema(description = "FCM 토큰", example = "d2YO_jG7Q4W0JH-dYw666M:APA9..")
                String token) {}
