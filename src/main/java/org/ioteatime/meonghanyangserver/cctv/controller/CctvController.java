package org.ioteatime.meonghanyangserver.cctv.controller;

import lombok.RequiredArgsConstructor;
import org.ioteatime.meonghanyangserver.cctv.dto.response.CctvInfoListResponse;
import org.ioteatime.meonghanyangserver.cctv.service.CctvService;
import org.ioteatime.meonghanyangserver.common.api.Api;
import org.ioteatime.meonghanyangserver.common.type.CctvSuccessType;
import org.ioteatime.meonghanyangserver.common.utils.LoginMember;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cctv")
public class CctvController implements CctvApi {
    private final CctvService cctvService;

    @DeleteMapping("/{cctvId}/out")
    public Api<?> out(@LoginMember Long userId, @PathVariable("cctvId") Long cctvId) {
        cctvService.outById(userId, cctvId);
        return Api.success(CctvSuccessType.DELETE_CCTV);
    }

    @GetMapping("/list/{groupId}")
    public Api<CctvInfoListResponse> cctvInfoList(
            @LoginMember Long memberId, @PathVariable Long groupId) {
        CctvInfoListResponse cctvInfoListResponse = cctvService.cctvInfoList(memberId, groupId);
        return Api.success(CctvSuccessType.GET_CCTV_DETAIL_LIST, cctvInfoListResponse);
    }
}
