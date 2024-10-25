package org.ioteatime.meonghanyangserver.user.controller;

import lombok.RequiredArgsConstructor;
import org.ioteatime.meonghanyangserver.common.api.Api;
import org.ioteatime.meonghanyangserver.user.dto.response.UserSimpleResponse;
import org.ioteatime.meonghanyangserver.user.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/openApi/auth")
public class UserController {

    private final UserService userService;

    // Email 중복 확인
    @GetMapping("/checkEmail/{email}")
    public Api<UserSimpleResponse> duplicateEmail(@PathVariable String email) {
        UserSimpleResponse response = userService.userProcess(email);
        return Api.OK(response);
    }
}