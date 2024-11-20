package org.ioteatime.meonghanyangserver.cctv.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.ioteatime.meonghanyangserver.cctv.dto.request.CreateCctvRequest;
import org.ioteatime.meonghanyangserver.cctv.dto.response.CctvInfoResponse;
import org.ioteatime.meonghanyangserver.common.api.Api;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "CCTV Device Api", description = "CCTV 기기 관련 API 목록입니다.")
public interface CctvDeviceApi {
    @Operation(summary = "CCTV 기기 생성 요청", description = "담당자: 임지인")
    Api<?> createCctv(@RequestBody CreateCctvRequest createCctvRequest);

    @Operation(summary = "CCTV 정보", description = "담당자: 최민석")
    Api<CctvInfoResponse> cctvInfo(@PathVariable Long cctvId);
}
