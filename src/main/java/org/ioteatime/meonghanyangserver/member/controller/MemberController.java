package org.ioteatime.meonghanyangserver.member.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ioteatime.meonghanyangserver.auth.dto.reponse.RefreshResponse;
import org.ioteatime.meonghanyangserver.common.api.Api;
import org.ioteatime.meonghanyangserver.common.type.AuthSuccessType;
import org.ioteatime.meonghanyangserver.common.type.GroupSuccessType;
import org.ioteatime.meonghanyangserver.common.utils.LoginMember;
import org.ioteatime.meonghanyangserver.group.dto.response.UpdateNicknameAndGroupNameResponse;
import org.ioteatime.meonghanyangserver.member.dto.request.ChangePasswordRequest;
import org.ioteatime.meonghanyangserver.member.dto.request.UpdateNicknameAndGroupNameRequest;
import org.ioteatime.meonghanyangserver.member.dto.response.MemberWithGroupDetailResponse;
import org.ioteatime.meonghanyangserver.member.service.MemberService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController implements MemberApi {
    private final MemberService memberService;

    @GetMapping("/{memberId}")
    public Api<MemberWithGroupDetailResponse> getMemberDetail(
            @PathVariable("memberId") Long memberId) {
        MemberWithGroupDetailResponse userDto = memberService.getMemberDetail(memberId);
        return Api.success(AuthSuccessType.GET_USER_DETAIL, userDto);
    }

    @DeleteMapping
    public Api<?> deleteMember(
            @RequestHeader("Authorization") String authHeader, @LoginMember Long memberId) {
        memberService.deleteMember(authHeader, memberId);
        return Api.success(AuthSuccessType.DELETE_USER);
    }

    @PutMapping("/password")
    public Api<?> changeMemberPassword(
            @LoginMember Long memberId, @Valid @RequestBody ChangePasswordRequest request) {
        memberService.changeMemberPassword(memberId, request);
        return Api.success(AuthSuccessType.UPDATE_PASSWORD);
    }

    @PostMapping("/refresh-token")
    public Api<RefreshResponse> refreshToken(
            @RequestHeader("Authorization") String authorizationHeader) {
        RefreshResponse refreshResponse = memberService.reissueAccessToken(authorizationHeader);
        return Api.success(AuthSuccessType.REISSUE_ACCESS_TOKEN, refreshResponse);
    }

    @PatchMapping("/nickname-groupname")
    public Api<UpdateNicknameAndGroupNameResponse> updateNicknameAndGroupName(
            @LoginMember Long memberId,
            @Valid @RequestBody UpdateNicknameAndGroupNameRequest request) {
        UpdateNicknameAndGroupNameResponse response =
                memberService.updateNicknameAndGroupName(memberId, request);
        if (response.groupName() == null) {
            return Api.success(AuthSuccessType.UPDATE_NICKNAME, response);
        } else if (response.nickname() == null) {
            return Api.success(GroupSuccessType.UPDATE_GROUP_NAME, response);
        } else {
            return Api.success(AuthSuccessType.UPDATE_NICKNAME_AND_GROUP_NAME, response);
        }
    }

    @PostMapping("/sign-out")
    public Api<?> logout() {
        return Api.success(AuthSuccessType.SIGN_OUT);
    }
}
