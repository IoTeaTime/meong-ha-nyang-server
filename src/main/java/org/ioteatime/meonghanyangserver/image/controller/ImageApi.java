package org.ioteatime.meonghanyangserver.image.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.ioteatime.meonghanyangserver.common.api.Api;
import org.ioteatime.meonghanyangserver.image.dto.response.GroupDateImageResponse;

@Tag(name = "Image Api", description = "Image 관련 API 목록입니다.")
public interface ImageApi {
    @Operation(
            summary = "회원이 속한 그룹의 날짜별 객체 탐지 이미지 목록을 최근 시간 순서대로 조회합니다.",
            description =
                    "담당자: 양원채\n\n달력의 날짜를 클릭했을 때 해당 날짜의 이미지 목록을 조회할 떄 사용합니다.\n\nURL QueryParameter로 날짜를 담아 요청 주시면 됩니다.")
    Api<GroupDateImageResponse> groupDateImageList(Long memberId, Short year, Byte month, Byte day);
}
