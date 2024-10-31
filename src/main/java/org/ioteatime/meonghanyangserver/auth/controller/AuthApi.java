package org.ioteatime.meonghanyangserver.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.ioteatime.meonghanyangserver.auth.dto.reponse.LoginResponse;
import org.ioteatime.meonghanyangserver.auth.dto.reponse.RefreshResponse;
import org.ioteatime.meonghanyangserver.auth.dto.request.EmailRequest;
import org.ioteatime.meonghanyangserver.auth.dto.request.LoginRequest;
import org.ioteatime.meonghanyangserver.common.api.Api;
import org.ioteatime.meonghanyangserver.user.dto.request.JoinRequest;
import org.ioteatime.meonghanyangserver.user.dto.response.UserSimpleResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@Tag(name = "Auth Api", description = "인증 관련 API 목록입니다.")
public interface AuthApi {
    @Operation(summary = "회원 가입을 합니다.")
    Api<Object> registerUser(@Valid @RequestBody JoinRequest userDto);

    @Operation(summary = "인증 메일 전송")
    Api<?> verifyEmail(@Valid @RequestBody EmailRequest email);

    @Operation(summary = "로그인을 합니다.")
    Api<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest);

    @Operation(summary = "이메일 중복을 확인 합니다.")
    Api<UserSimpleResponse> duplicateEmail(@Valid @RequestBody EmailRequest email);

    @Operation(summary = "토큰을 다시 생성합니다.")
    Api<RefreshResponse> refreshToken(@RequestHeader("Authorization") String authorizationHeader);
}
