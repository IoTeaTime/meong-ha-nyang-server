package org.ioteatime.meonghanyangserver.cctv.controller;

import lombok.RequiredArgsConstructor;
import org.ioteatime.meonghanyangserver.cctv.dto.request.CreateCctvRequest;
import org.ioteatime.meonghanyangserver.cctv.dto.response.CctvInfoResponse;
import org.ioteatime.meonghanyangserver.cctv.service.CctvService;
import org.ioteatime.meonghanyangserver.common.api.Api;
import org.ioteatime.meonghanyangserver.common.type.CctvSuccessType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/open-api/cctv")
public class CctvDeviceController implements CctvDeviceApi {
    private final CctvService cctvService;

    @PostMapping
    public Api<?> createCctv(@RequestBody CreateCctvRequest createCctvRequest) {
        cctvService.createCctv(createCctvRequest);
        return Api.success(CctvSuccessType.CREATE_CCTV);
    }

    @GetMapping("/{cctvId}")
    public Api<CctvInfoResponse> cctvInfo(@PathVariable Long cctvId) {
        CctvInfoResponse cctvInfoResponse = cctvService.cctvInfo(cctvId);
        return Api.success(CctvSuccessType.GET_CCTV_DETAIL, cctvInfoResponse);
    }
}
