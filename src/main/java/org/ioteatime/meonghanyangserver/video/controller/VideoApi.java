package org.ioteatime.meonghanyangserver.video.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDate;
import org.ioteatime.meonghanyangserver.common.api.Api;

@Tag(name = "Video Api", description = "Video 관련 API 목록입니다.")
public interface VideoApi {
    @Operation(summary = "날짜를 사용한 동영상 정보 조회 발급받습니다.", description = "담당자: 최민석")
    Api<?> searchToDate(Long memberId, Long groupId, LocalDate date);
}
