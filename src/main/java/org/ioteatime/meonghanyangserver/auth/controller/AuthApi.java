package org.ioteatime.meonghanyangserver.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.ioteatime.meonghanyangserver.auth.dto.reponse.LoginResponse;
import org.ioteatime.meonghanyangserver.auth.dto.reponse.RefreshResponse;
<<<<<<< HEAD
import org.ioteatime.meonghanyangserver.auth.dto.request.EmailRequest;
=======
>>>>>>> 62ffa31dd136cdd1181a8eebe8d5a6380205b3bf
import org.ioteatime.meonghanyangserver.auth.dto.request.LoginRequest;
import org.ioteatime.meonghanyangserver.common.api.Api;
import org.ioteatime.meonghanyangserver.user.dto.UserDto;
import org.ioteatime.meonghanyangserver.user.dto.response.UserSimpleResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@Tag(name = "Auth Api", description = "인증 관련 API 목록입니다.")
public interface AuthApi {
    @Operation(summary = "회원 가입을 합니다.")
    Api<Object> registerUser(@Valid @RequestBody UserDto userDto);

    @Operation(summary = "인증 메일 전송")
    Api<?> verifyEmail(@Valid @RequestBody EmailRequest email);

    @Operation(summary = "로그인을 합니다.")
    Api<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest);

<<<<<<< HEAD
    @Operation(summary = "이메일 중복을 확인 합니다.")
    Api<UserSimpleResponse> duplicateEmail(@Valid @RequestBody EmailRequest email);

=======
>>>>>>> 62ffa31dd136cdd1181a8eebe8d5a6380205b3bf
    @Operation(summary = "토큰을 다시 생성합니다.")
    Api<RefreshResponse> refreshToken(@RequestHeader("Authorization") String authorizationHeader);
}
