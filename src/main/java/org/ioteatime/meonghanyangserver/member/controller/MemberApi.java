package org.ioteatime.meonghanyangserver.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.ioteatime.meonghanyangserver.auth.dto.reponse.RefreshResponse;
import org.ioteatime.meonghanyangserver.common.api.Api;
import org.ioteatime.meonghanyangserver.common.utils.LoginMember;
import org.ioteatime.meonghanyangserver.group.dto.response.UpdateNicknameAndGroupNameResponse;
import org.ioteatime.meonghanyangserver.member.dto.request.ChangePasswordRequest;
import org.ioteatime.meonghanyangserver.member.dto.request.UpdateNicknameAndGroupNameRequest;
import org.ioteatime.meonghanyangserver.member.dto.response.MemberWithGroupDetailResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@Tag(name = "User Api", description = "유저 관련 API 목록입니다.")
public interface MemberApi {

    @Operation(summary = "회원 상세 정보를 조회합니다.", description = "담당자: 임지인")
    Api<MemberWithGroupDetailResponse> getMemberDetail(@PathVariable("memberId") Long memberId);

    @Operation(summary = "회원 정보를 삭제합니다.", description = "담당자: 임지인")
    Api<Object> deleteMember(@LoginMember Long memberId);

    @Operation(summary = "회원의 비밀번호를 변경합니다.", description = "담당자: 임지인")
    Api<Object> changeMemberPassword(
            @LoginMember Long memberId, @RequestBody ChangePasswordRequest request);

    @Operation(summary = "Access 토큰을 다시 생성합니다.", description = "담당자: 서유진")
    Api<RefreshResponse> refreshToken(@RequestHeader("Authorization") String authorizationHeader);

    @Operation(
            summary = "회원 닉네임과 회원이 소속된 그룹명을 변경합니다.",
            description =
                    "담당자: 양원채\n\n닉네임 또는 그룹명을 갱신할 수 있으며, 한꺼번에 갱신도 가능합니다.\n\n갱신하고 싶지 않은 필드는 아예 적지 않거나 null로 담아 요청하시면 됩니다. 응답으로는 변경된 사항만 응답합니다.")
    Api<UpdateNicknameAndGroupNameResponse> updateNicknameAndGroupName(
            @LoginMember Long memberId, @RequestBody UpdateNicknameAndGroupNameRequest request);

    @Operation(summary = "로그아웃을 진행합니다.", description = "담당자: 최민석")
    Api<?> logout();
}
