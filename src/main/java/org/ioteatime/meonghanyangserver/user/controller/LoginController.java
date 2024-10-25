package org.ioteatime.meonghanyangserver.user.controller;

import lombok.RequiredArgsConstructor;
import org.ioteatime.meonghanyangserver.common.api.Api;
import org.ioteatime.meonghanyangserver.user.dto.request.LoginDto;
import org.ioteatime.meonghanyangserver.user.dto.response.LoginResponse;
import org.ioteatime.meonghanyangserver.user.service.LoginService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/open-api/auth")
public class LoginController implements LoginApi {
    private final LoginService loginService;

    @Override
    @PostMapping("/sign-in")
    public Api<LoginResponse> login(LoginDto loginDto) {
        LoginResponse loginResponse = loginService.login(loginDto);
        return Api.CREATE(loginResponse);
    }
}
