package org.ioteatime.meonghanyangserver.cctv.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ioteatime.meonghanyangserver.cctv.dto.request.CreateCctvRequest;
import org.ioteatime.meonghanyangserver.cctv.dto.response.CctvSelfInfoResponse;
import org.ioteatime.meonghanyangserver.cctv.dto.response.CreateCctvResponse;
import org.ioteatime.meonghanyangserver.cctv.service.CctvService;
import org.ioteatime.meonghanyangserver.common.api.Api;
import org.ioteatime.meonghanyangserver.common.type.CctvSuccessType;
import org.ioteatime.meonghanyangserver.common.utils.LoginMember;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cctv-device")
public class CctvDeviceController implements CctvDeviceApi {
    private final CctvService cctvService;

    @PostMapping
    public Api<CreateCctvResponse> createCctv(
            @Valid @RequestBody CreateCctvRequest createCctvRequest) {
        CreateCctvResponse createCctvResponse = cctvService.createCctv(createCctvRequest);
        return Api.success(CctvSuccessType.CREATE_CCTV, createCctvResponse);
    }

    @GetMapping
    public Api<CctvSelfInfoResponse> cctvInfo(@LoginMember Long cctvId) {
        CctvSelfInfoResponse cctvSelfInfoResponse = cctvService.cctvSelfInfo(cctvId);
        return Api.success(CctvSuccessType.GET_CCTV_DETAIL, cctvSelfInfoResponse);
    }
}
