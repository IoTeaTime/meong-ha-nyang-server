package org.ioteatime.meonghanyangserver.fcm.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "FCM Topic 응답")
public record FcmTopicResponse(
        @NotBlank @Schema(description = "FCM Topic", example = "1_ERFTSGT") String fcmTopic) {}
