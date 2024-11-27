package org.ioteatime.meonghanyangserver.video.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDate;
import org.ioteatime.meonghanyangserver.common.api.Api;
import org.ioteatime.meonghanyangserver.common.utils.LoginMember;
import org.ioteatime.meonghanyangserver.video.dto.response.VideoPresignedUrlResponse;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Video Api", description = "Video 관련 API 목록입니다.")
public interface VideoApi {
    @Operation(summary = "동영상의 presigned url을 발급받습니다.", description = "담당자: 최민석")
    public Api<VideoPresignedUrlResponse> getVideoPresignedUrl(
            @LoginMember Long memberId, @PathVariable Long videoId, @PathVariable Long groupId);

    @Operation(summary = "날짜를 사용한 동영상 정보 조회 발급받습니다.", description = "담당자: 최민석")
    public Api<?> searchToDate(
            @LoginMember Long memberId,
            @PathVariable Long groupId,
            @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date);
}
