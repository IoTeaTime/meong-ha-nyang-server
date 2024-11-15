package org.ioteatime.meonghanyangserver.cctv.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.ioteatime.meonghanyangserver.common.api.Api;
import org.ioteatime.meonghanyangserver.common.utils.LoginMember;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "CCTV Api", description = "CCTV 관련 API 목록입니다.")
public interface CctvApi {
    @Operation(summary = "MASTER 회원이 CCTV 퇴출", description = "담당자: 양원채")
    Api<?> out(@LoginMember Long userId, @PathVariable Long cctvId);
}
