package org.ioteatime.meonghanyangserver.user.controller;

import lombok.RequiredArgsConstructor;
import org.ioteatime.meonghanyangserver.common.api.Api;
import org.ioteatime.meonghanyangserver.user.dto.response.UserDetailResponse;
import org.ioteatime.meonghanyangserver.user.service.UserService;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("/{userId}")
    public Api<Object> deleteUser(Long userId) {
        userService.deleteUser(userId);
        return Api.OK();
    }

    @PutMapping("/{userId}")
    public Api<Object> changeUserPassword(Long userId, String newPassword) {
        userService.changeUserPassword(userId, newPassword);
        return Api.OK();
    }
}
