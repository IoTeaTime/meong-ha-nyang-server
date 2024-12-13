package org.ioteatime.meonghanyangserver.fcm.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.ioteatime.meonghanyangserver.common.api.Api;
import org.ioteatime.meonghanyangserver.fcm.dto.request.SaveFcmTokenRequest;
import org.ioteatime.meonghanyangserver.fcm.dto.response.FcmTopicResponse;

@Tag(name = "Fcm Api", description = "알림 관련 API 목록입니다.")
public interface FcmApi {
    @Operation(summary = "FCM 토큰 저장", description = "담당자: 양원채")
    Api<?> saveToken(Long memberId, SaveFcmTokenRequest createFcmTokenRequest);

    @Operation(summary = "그룹의 FCM 토픽 조회", description = "담당자: 양원채")
    Api<FcmTopicResponse> findFcmTopicByGroupId(Long memberId);
}
