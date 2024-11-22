package org.ioteatime.meonghanyangserver.member.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ioteatime.meonghanyangserver.auth.dto.reponse.RefreshResponse;
import org.ioteatime.meonghanyangserver.common.api.Api;
import org.ioteatime.meonghanyangserver.common.type.AuthSuccessType;
import org.ioteatime.meonghanyangserver.common.utils.LoginMember;
import org.ioteatime.meonghanyangserver.member.dto.request.ChangePasswordRequest;
import org.ioteatime.meonghanyangserver.member.dto.response.MemberDetailResponse;
import org.ioteatime.meonghanyangserver.member.service.MemberService;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController implements MemberApi {
    private final MemberService memberService;

    @GetMapping("/{memberId}")
    public Api<MemberDetailResponse> getMemberDetail(@PathVariable("memberId") Long memberId) {
        MemberDetailResponse userDto = memberService.getMemberDetail(memberId);
        return Api.success(AuthSuccessType.GET_USER_DETAIL, userDto);
    }

    @DeleteMapping
    public Api<Object> deleteMember(@LoginMember Long memberId) {
        memberService.deleteMember(memberId);
        return Api.success(AuthSuccessType.DELETE_USER);
    }

    @PutMapping("/password")
    public Api<Object> changeMemberPassword(
            @LoginMember Long memberId, @RequestBody @Valid ChangePasswordRequest request) {
        memberService.changeMemberPassword(memberId, request);
        return Api.success(AuthSuccessType.UPDATE_PASSWORD);
    }

    @PostMapping("/refresh-token")
    public Api<RefreshResponse> refreshToken(
            @RequestHeader("Authorization") String authorizationHeader) {
        RefreshResponse refreshResponse = memberService.reissueAccessToken(authorizationHeader);
        return Api.success(AuthSuccessType.REISSUE_ACCESS_TOKEN, refreshResponse);
    }

    @PostMapping("/sign-out")
    public Api<?> logout(@LoginMember Long memberId,@RequestHeader("Authorization") String accessToken) {
        memberService.logout(memberId,accessToken);
        return Api.success(AuthSuccessType.SIGN_OUT);
    }
}
