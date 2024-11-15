package org.ioteatime.meonghanyangserver.cctv.controller;

import lombok.RequiredArgsConstructor;
import org.ioteatime.meonghanyangserver.cctv.dto.request.CreateCctvRequest;
import org.ioteatime.meonghanyangserver.cctv.service.CctvService;
import org.ioteatime.meonghanyangserver.common.api.Api;
import org.ioteatime.meonghanyangserver.common.type.CctvSuccessType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
