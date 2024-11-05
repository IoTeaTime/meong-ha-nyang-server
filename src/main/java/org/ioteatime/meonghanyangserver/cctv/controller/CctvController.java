package org.ioteatime.meonghanyangserver.cctv.controller;

import lombok.RequiredArgsConstructor;
import org.ioteatime.meonghanyangserver.cctv.service.CctvService;
import org.ioteatime.meonghanyangserver.common.api.Api;
import org.ioteatime.meonghanyangserver.common.type.CctvSuccessType;
import org.ioteatime.meonghanyangserver.common.utils.LoginMember;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cctv")
public class CctvController implements CctvApi {
    private final CctvService cctvService;

    @DeleteMapping("/{cctvId}")
    public Api<?> delete(@LoginMember Long userId, @PathVariable("cctvId") Long cctvId) {
        cctvService.deleteById(userId, cctvId);
        return Api.success(CctvSuccessType.DELETE_CCTV);
    }
}
