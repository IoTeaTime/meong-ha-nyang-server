package org.ioteatime.meonghanyangserver.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ioteatime.meonghanyangserver.auth.dto.reponse.LoginResponse;
import org.ioteatime.meonghanyangserver.auth.dto.reponse.RefreshResponse;
import org.ioteatime.meonghanyangserver.auth.dto.request.EmailRequest;
import org.ioteatime.meonghanyangserver.auth.dto.request.LoginRequest;
import org.ioteatime.meonghanyangserver.auth.service.AuthService;
import org.ioteatime.meonghanyangserver.common.api.Api;
import org.ioteatime.meonghanyangserver.user.dto.UserDto;
import org.ioteatime.meonghanyangserver.user.dto.response.UserSimpleResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/open-api/auth")
public class AuthController implements AuthApi {
    private final AuthService authService;

    @PostMapping("/sign-up")
    public Api<Object> registerUser(@Valid @RequestBody UserDto userDto) {
        authService.joinProcess(userDto);
        return Api.CREATE();
    }

    @PostMapping("/email-verification")
    public Api<?> verifyEmail(@Valid @RequestBody EmailRequest emailReq) {
        authService.send(emailReq.email());
        return Api.OK();
    }

    // Email 중복 확인
    @PostMapping("/check-email")
    public Api<UserSimpleResponse> duplicateEmail(@Valid @RequestBody EmailRequest emailReq) {
        UserSimpleResponse response = authService.verifyEmail(emailReq.email());
        return Api.OK(response);
    }

    @Override
    @PostMapping("/sign-in")
    public Api<LoginResponse> login(LoginRequest loginRequest) {
        LoginResponse loginResponse = authService.login(loginRequest);
        return Api.CREATE(loginResponse);
    }

    @PostMapping("/refresh-token")
    public Api<RefreshResponse> refreshToken(
            @RequestHeader("Authorization") String authorizationHeader) {
        RefreshResponse refreshResponse = authService.reissueAccessToken(authorizationHeader);
        return Api.OK(refreshResponse);
    }
}
