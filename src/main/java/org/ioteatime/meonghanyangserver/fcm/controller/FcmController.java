package org.ioteatime.meonghanyangserver.fcm.controller;

import lombok.RequiredArgsConstructor;
import org.ioteatime.meonghanyangserver.common.api.Api;
import org.ioteatime.meonghanyangserver.common.type.FcmSuccessType;
import org.ioteatime.meonghanyangserver.fcm.dto.request.SaveFcmTokenRequest;
import org.ioteatime.meonghanyangserver.fcm.service.FcmService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/open-api/fcm") // 테스트 후 api로 변경하기
public class FcmController implements FcmApi {
    private final FcmService fcmService;

    @PostMapping("/token")
    public Api<?> saveToken(SaveFcmTokenRequest createFcmTokenRequest) {
        fcmService.saveToken(createFcmTokenRequest.token());
        return Api.success(FcmSuccessType.SAVE);
    }
}
