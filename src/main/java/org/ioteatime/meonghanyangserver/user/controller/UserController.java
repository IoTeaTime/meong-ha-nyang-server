package org.ioteatime.meonghanyangserver.user.controller;

import lombok.RequiredArgsConstructor;
import org.ioteatime.meonghanyangserver.common.api.Api;
import org.ioteatime.meonghanyangserver.user.dto.response.UserDetailResponse;
import org.ioteatime.meonghanyangserver.user.service.UserService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController implements UserApi {
    private final UserService userService;

    @GetMapping("/{userId}")
    public Api<UserDetailResponse> getUserDetail(Long userId) {
        UserDetailResponse userDto = userService.getUserDetail(userId);
        return Api.OK(userDto);
    }

    @DeleteMapping("/edit/delete/{userId}")
    public Api<Object> deleteUser(Long userId) {
        userService.deleteUser(userId);
        return Api.OK();
    }
}
