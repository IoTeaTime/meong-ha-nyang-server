package org.ioteatime.meonghanyangserver.cctv.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.ioteatime.meonghanyangserver.cctv.dto.request.CreateCctvRequest;
import org.ioteatime.meonghanyangserver.cctv.dto.response.CctvSelfInfoResponse;
import org.ioteatime.meonghanyangserver.cctv.dto.response.CreateCctvResponse;
import org.ioteatime.meonghanyangserver.common.api.Api;
import org.ioteatime.meonghanyangserver.common.utils.LoginMember;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "CCTV Device Api", description = "CCTV 기기에서 요청할 수 있는 API 목록입니다.")
public interface CctvDeviceApi {
    // 토큰이 없어도 가능해야 함
    @Operation(summary = "CCTV 기기 생성 요청", description = "담당자: 임지인")
    Api<CreateCctvResponse> createCctv(@RequestBody CreateCctvRequest createCctvRequest);

    @Operation(summary = "CCTV 정보", description = "담당자: 최민석")
    Api<CctvSelfInfoResponse> cctvInfo(@LoginMember Long cctvId);
}
