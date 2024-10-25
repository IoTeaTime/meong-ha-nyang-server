package org.ioteatime.meonghanyangserver.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ioteatime.meonghanyangserver.auth.dto.reponse.LoginResponse;
import org.ioteatime.meonghanyangserver.auth.dto.request.LoginRequest;
import org.ioteatime.meonghanyangserver.auth.dto.request.SendEmailRequest;
import org.ioteatime.meonghanyangserver.auth.service.AuthService;
import org.ioteatime.meonghanyangserver.common.api.Api;
import org.ioteatime.meonghanyangserver.user.dto.UserDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public Api<?> verifyEmail(@Valid @RequestBody SendEmailRequest sendEmailReq) {
        authService.send(sendEmailReq.email());
        return Api.OK();
    }

    @Override
    @PostMapping("/sign-in")
    public Api<LoginResponse> login(LoginRequest loginRequest) {
        LoginResponse loginResponse = authService.login(loginRequest);
        return Api.CREATE(loginResponse);
    }
}
