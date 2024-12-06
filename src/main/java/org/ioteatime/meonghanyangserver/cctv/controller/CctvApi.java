package org.ioteatime.meonghanyangserver.cctv.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.ioteatime.meonghanyangserver.cctv.dto.request.UpdateCctvNickname;
import org.ioteatime.meonghanyangserver.cctv.dto.response.CctvInfoListResponse;
import org.ioteatime.meonghanyangserver.cctv.dto.response.CctvInfoResponse;
import org.ioteatime.meonghanyangserver.common.api.Api;
import org.ioteatime.meonghanyangserver.common.utils.LoginMember;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "CCTV Api", description = "CCTV 관련 API 목록입니다.")
public interface CctvApi {
    @Operation(summary = "MASTER 회원이 CCTV 퇴출", description = "담당자: 양원채")
    Api<?> out(@LoginMember Long userId, @PathVariable Long cctvId);

    @Operation(summary = "CCTV 정보 목록 조회", description = "담당자: 최민석")
    Api<CctvInfoListResponse> cctvInfoList(@LoginMember Long memberId, @PathVariable Long groupId);

    @Operation(summary = "CCTV 이름 변경", description = "담당자: 양원채")
    Api<CctvInfoResponse> updateNickName(
            @LoginMember Long memberId, @RequestBody UpdateCctvNickname request);

    @Operation(summary = "CCTV 정보", description = "담당자: 양원채")
    Api<CctvInfoResponse> cctvInfo(@PathVariable("cctvId") Long cctvId);
}
