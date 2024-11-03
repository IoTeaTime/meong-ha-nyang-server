package org.ioteatime.meonghanyangserver.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.ioteatime.meonghanyangserver.auth.dto.reponse.LoginResponse;
import org.ioteatime.meonghanyangserver.auth.dto.request.EmailRequest;
import org.ioteatime.meonghanyangserver.auth.dto.request.LoginRequest;
import org.ioteatime.meonghanyangserver.auth.dto.request.VerifyEmailRequest;
import org.ioteatime.meonghanyangserver.common.api.Api;
import org.ioteatime.meonghanyangserver.member.dto.request.JoinRequest;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Auth Api", description = "인증 관련 API 목록입니다.")
public interface AuthApi {
    @Operation(summary = "회원 가입을 합니다.")
    Api<Object> registerUser(@Valid @RequestBody JoinRequest userDto);

    @Operation(summary = "인증 메일 전송")
    Api<?> sendEmailCode(@Valid @RequestBody EmailRequest email);

    @Operation(summary = "메일 인증 코드 검증")
    Api<?> verifyEmail(@Valid @RequestBody VerifyEmailRequest verifyEmailRequest);

    @Operation(summary = "로그인을 합니다.")
    Api<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest);

    @Operation(summary = "이메일 중복을 확인 합니다.")
    Api<?> duplicateEmail(@Valid @RequestBody EmailRequest email);
}
