package org.ioteatime.meonghanyangserver.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.ioteatime.meonghanyangserver.auth.dto.reponse.LoginResponse;
import org.ioteatime.meonghanyangserver.auth.dto.request.LoginRequest;
import org.ioteatime.meonghanyangserver.auth.dto.request.SendEmailRequest;
import org.ioteatime.meonghanyangserver.common.api.Api;
import org.ioteatime.meonghanyangserver.user.dto.UserDto;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Auth Api", description = "인증 관련 API 목록입니다.")
public interface AuthApi {
    @Operation(summary = "회원 가입을 합니다.")
    Api<Object> registerUser(@Valid @RequestBody UserDto userDto);

    @Operation(summary = "인증 메일 전송")
    Api<?> verifyEmail(@Valid @RequestBody SendEmailRequest email);

    @Operation(summary = "로그인을 합니다.")
    Api<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest);
}
