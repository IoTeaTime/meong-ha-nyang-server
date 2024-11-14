package org.ioteatime.meonghanyangserver.fcm.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.ioteatime.meonghanyangserver.common.api.Api;
import org.ioteatime.meonghanyangserver.fcm.dto.request.SaveFcmTokenRequest;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Fcm Api", description = "알림 관련 API 목록입니다.")
public interface FcmApi {
    @Operation(summary = "FCM 토큰 저장", description = "담당자: 양원채")
    Api<?> saveToken(@RequestBody SaveFcmTokenRequest createFcmTokenRequest);
}
