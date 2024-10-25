package org.ioteatime.meonghanyangserver.user.controller;

import lombok.RequiredArgsConstructor;
import org.ioteatime.meonghanyangserver.common.api.Api;
import org.ioteatime.meonghanyangserver.user.dto.response.UserDetailResponse;
import org.ioteatime.meonghanyangserver.user.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController implements UserApi {

    private final UserService userService;

    @Override
    public Api<UserDetailResponse> getUserDetail(Long userId) {
        UserDetailResponse userDto = userService.getUserDetail(userId);
        return Api.OK(userDto);
    }
}
