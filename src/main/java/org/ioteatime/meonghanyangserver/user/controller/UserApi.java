package org.ioteatime.meonghanyangserver.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.ioteatime.meonghanyangserver.common.api.Api;
import org.ioteatime.meonghanyangserver.user.dto.response.UserDetailResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "User Api", description = "유저 관련 API 목록입니다.")
public interface UserApi {

    @Operation(summary = "회원 상세 정보를 조회합니다.")
    Api<UserDetailResponse> getUserDetail(@PathVariable("userId") Long userId);

    @Operation(summary = "회원 정보를 삭제합니다.")
    Api<Object> deleteUser(@PathVariable("userId") Long userId);

    @Operation(summary = "회원의 비밀번호를 변경합니다.")
    Api<Object> changeUserPassword(
            @PathVariable("userId") Long userId, @RequestBody String newPassword);
}
