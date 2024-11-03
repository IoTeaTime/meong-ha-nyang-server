package org.ioteatime.meonghanyangserver.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.ioteatime.meonghanyangserver.auth.dto.reponse.RefreshResponse;
import org.ioteatime.meonghanyangserver.common.api.Api;
import org.ioteatime.meonghanyangserver.common.utils.LoginMember;
import org.ioteatime.meonghanyangserver.member.dto.request.ChangePasswordRequest;
import org.ioteatime.meonghanyangserver.member.dto.response.MemberDetailResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@Tag(name = "User Api", description = "유저 관련 API 목록입니다.")
public interface MemberApi {

    @Operation(summary = "회원 상세 정보를 조회합니다.")
    Api<MemberDetailResponse> getMemberDetail(@PathVariable("memberId") Long memberId);

    @Operation(summary = "회원 정보를 삭제합니다.")
    Api<Object> deleteMember(@LoginMember Long memberId);

    @Operation(summary = "회원의 비밀번호를 변경합니다.")
    Api<Object> changeMemberPassword(
            @LoginMember Long memberId, @RequestBody @Valid ChangePasswordRequest request);

    @Operation(summary = "Access 토큰을 다시 생성합니다.")
    Api<RefreshResponse> refreshToken(@RequestHeader("Authorization") String authorizationHeader);
}
