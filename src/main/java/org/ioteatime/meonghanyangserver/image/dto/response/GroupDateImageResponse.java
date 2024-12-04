package org.ioteatime.meonghanyangserver.image.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "날짜에 해당하는 그룹의 이미지 목록 응답")
public record GroupDateImageResponse(
        @Schema(description = "날짜에 해당하는 그룹의 이미지 목록") List<ImageResponse> images) {}
