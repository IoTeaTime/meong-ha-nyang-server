package org.ioteatime.meonghanyangserver.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ioteatime.meonghanyangserver.common.api.Api;
import org.ioteatime.meonghanyangserver.user.dto.UserDto;
import org.ioteatime.meonghanyangserver.user.service.JoinService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/open-api/auth")
public class JoinController implements JoinApi {
    private final JoinService joinService;

    @PostMapping("/sign-up")
    public Api<Object> registerUser(@Valid @RequestBody UserDto userDto) {
        joinService.joinProcess(userDto);
        return Api.CREATE();
    }
}
