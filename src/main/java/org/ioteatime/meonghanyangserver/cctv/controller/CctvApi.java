package org.ioteatime.meonghanyangserver.cctv.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.ioteatime.meonghanyangserver.cctv.dto.request.UpdateCctvNickname;
import org.ioteatime.meonghanyangserver.cctv.dto.response.CctvInfoListResponse;
import org.ioteatime.meonghanyangserver.cctv.dto.response.CctvInfoResponse;
import org.ioteatime.meonghanyangserver.common.api.Api;

@Tag(name = "CCTV Api", description = "CCTV 관련 API 목록입니다.")
public interface CctvApi {
    @Operation(summary = "MASTER 회원이 CCTV 퇴출", description = "담당자: 양원채")
    Api<?> out(Long userId, Long cctvId);

    @Operation(summary = "CCTV 정보 목록 조회", description = "담당자: 최민석")
    Api<CctvInfoListResponse> cctvInfoList(Long memberId, Long groupId);

    @Operation(summary = "CCTV 이름 변경", description = "담당자: 양원채")
    Api<CctvInfoResponse> updateNickName(Long memberId, UpdateCctvNickname request);

    @Operation(summary = "CCTV 정보", description = "담당자: 양원채")
    Api<CctvInfoResponse> cctvInfo(Long cctvId);
}
