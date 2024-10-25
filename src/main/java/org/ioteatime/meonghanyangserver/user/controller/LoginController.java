package org.ioteatime.meonghanyangserver.user.controller;

import lombok.RequiredArgsConstructor;
import org.ioteatime.meonghanyangserver.common.api.Api;
import org.ioteatime.meonghanyangserver.user.dto.request.LoginDto;
import org.ioteatime.meonghanyangserver.user.dto.response.LoginResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class LoginController implements LoginApi{

    @Override
    public Api<LoginResponse> login(LoginDto loginDto) {
        return null;
    }
}
