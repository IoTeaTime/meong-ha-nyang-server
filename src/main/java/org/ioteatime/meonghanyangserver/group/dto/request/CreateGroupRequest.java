package org.ioteatime.meonghanyangserver.group.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "그룹 생성 정보 요청")
public record CreateGroupRequest(
        @NotBlank(message = "thing id는 필수 입력값")
                @Schema(description = "기기의 SSAID", example = "70a1b2c3d4e5f678")
                String thingId) {}
