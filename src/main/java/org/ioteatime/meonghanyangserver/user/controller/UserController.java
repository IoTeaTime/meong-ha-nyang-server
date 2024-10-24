package org.ioteatime.meonghanyangserver.user.controller;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.ioteatime.meonghanyangserver.common.api.Api;
import org.ioteatime.meonghanyangserver.user.domain.UserEntity;
import org.ioteatime.meonghanyangserver.user.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    // User의 고유 ID를 확인하여 회원 정보를 반환합니다.
    @GetMapping("/{userId}")
    public Api<Optional<UserEntity>> getUserDetail(
            @RequestHeader("Authorization") String authorizationHeader, @PathVariable Long userId) {

        Optional<UserEntity> userDto = userService.getUserDetail(userId);

        return Api.OK(userDto);
    }
}
