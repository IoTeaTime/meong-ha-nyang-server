package org.ioteatime.meonghanyangserver.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ioteatime.meonghanyangserver.auth.dto.reponse.LoginResponse;
import org.ioteatime.meonghanyangserver.auth.dto.request.EmailRequest;
import org.ioteatime.meonghanyangserver.auth.dto.request.IssuePasswordRequest;
import org.ioteatime.meonghanyangserver.auth.dto.request.LoginRequest;
import org.ioteatime.meonghanyangserver.auth.dto.request.VerifyEmailRequest;
import org.ioteatime.meonghanyangserver.auth.service.AuthService;
import org.ioteatime.meonghanyangserver.common.api.Api;
import org.ioteatime.meonghanyangserver.common.type.AuthSuccessType;
import org.ioteatime.meonghanyangserver.member.dto.request.JoinRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/open-api/auth")
public class AuthController implements AuthApi {
    private final AuthService authService;

    @PostMapping("/sign-up")
    public Api<?> registerUser(@Valid @RequestBody JoinRequest userDto) {
        authService.joinProcess(userDto);
        return Api.success(AuthSuccessType.SIGN_UP);
    }

    @PostMapping("/email-verification")
    public Api<?> sendEmailCode(@Valid @RequestBody EmailRequest emailReq) {
        authService.send(emailReq.email());
        return Api.success(AuthSuccessType.SEND_EMAIL_CODE);
    }

    @PostMapping("/check-verification")
    public Api<?> verifyEmail(@Valid @RequestBody VerifyEmailRequest verifyEmailRequest) {
        authService.verifyEmailCode(verifyEmailRequest.email(), verifyEmailRequest.code());
        return Api.success(AuthSuccessType.VERIFY_EMAIL_CODE);
    }

    // Email 중복 확인
    @PostMapping("/check-email")
    public Api<?> duplicateEmail(@Valid @RequestBody EmailRequest emailReq) {
        authService.verifyEmail(emailReq.email());
        return Api.success(AuthSuccessType.EMAIL_VERIFIED);
    }

    @PatchMapping("/change-password")
    public Api<?> issuePassword(@Valid @RequestBody IssuePasswordRequest issuePasswordRequest) {
        authService.issuePassword(issuePasswordRequest);
        return Api.success(AuthSuccessType.ISSUE_PASSWORD);
    }

    @PostMapping("/sign-in")
    public Api<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = authService.login(loginRequest);
        return Api.success(AuthSuccessType.SIGN_IN, loginResponse);
    }
}
