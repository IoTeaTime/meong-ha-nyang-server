package org.ioteatime.meonghanyangserver.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ioteatime.meonghanyangserver.auth.dto.request.EmailCheckRequest;
import org.ioteatime.meonghanyangserver.auth.dto.request.SendEmailRequest;
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
    public Api<?> verifyEmail(@Valid @RequestBody SendEmailRequest sendEmailReq) {
        authService.send(sendEmailReq.email());
        return Api.OK();
    }

    // Email 중복 확인
    @PostMapping("/check-email")
    public Api<UserSimpleResponse> duplicateEmail(
            @RequestBody EmailCheckRequest emailCheckRequest) {
        UserSimpleResponse response = authService.verifyEmail(emailCheckRequest);
        return Api.OK(response);
    }
}
