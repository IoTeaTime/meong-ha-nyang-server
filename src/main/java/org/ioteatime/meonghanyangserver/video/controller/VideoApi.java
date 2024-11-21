package org.ioteatime.meonghanyangserver.video.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.ioteatime.meonghanyangserver.common.api.Api;
import org.ioteatime.meonghanyangserver.common.utils.LoginMember;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "Video Api", description = "Video 관련 API 목록입니다.")
public interface VideoApi {
    @Operation(summary = "동영상의 presigned url을 발급받습니다.", description = "담당자: 최민석")
    public Api<?> getVideoPresignedUrl(
            @LoginMember Long memberId, @PathVariable Long videoId, @PathVariable Long groupId);
}
