package org.ioteatime.meonghanyangserver.cctv.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "cctv 정보 목록 응답")
public record CctvInfoListResponse(
        @Schema(description = "CCTV 정보 목록") List<CctvInfoResponse> cctv) {}
