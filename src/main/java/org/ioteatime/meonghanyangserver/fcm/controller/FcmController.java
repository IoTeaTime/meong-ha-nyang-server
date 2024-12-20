package org.ioteatime.meonghanyangserver.fcm.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ioteatime.meonghanyangserver.common.api.Api;
import org.ioteatime.meonghanyangserver.common.type.FcmSuccessType;
import org.ioteatime.meonghanyangserver.common.utils.LoginMember;
import org.ioteatime.meonghanyangserver.fcm.dto.request.SaveFcmTokenRequest;
import org.ioteatime.meonghanyangserver.fcm.dto.response.FcmTopicResponse;
import org.ioteatime.meonghanyangserver.fcm.service.FcmService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/fcm") // 테스트 후 api로 변경하기
public class FcmController implements FcmApi {
    private final FcmService fcmService;

    @PostMapping("/token")
    public Api<?> saveToken(
            @LoginMember Long memberId,
            @Valid @RequestBody SaveFcmTokenRequest createFcmTokenRequest) {
        fcmService.saveToken(memberId, createFcmTokenRequest.token());
        return Api.success(FcmSuccessType.SAVE);
    }

    @GetMapping("/topic/group")
    public Api<FcmTopicResponse> findFcmTopicByGroupId(@LoginMember Long memberId) {
        FcmTopicResponse response = fcmService.findFcmTopicByGroupId(memberId);
        return Api.success(FcmSuccessType.GROUP_TOPIC, response);
    }
}
