package org.ioteatime.meonghanyangserver.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ioteatime.meonghanyangserver.auth.dto.reponse.RefreshResponse;
import org.ioteatime.meonghanyangserver.common.api.Api;
import org.ioteatime.meonghanyangserver.common.type.AuthSuccessType;
import org.ioteatime.meonghanyangserver.common.utils.LoginMember;
import org.ioteatime.meonghanyangserver.user.dto.request.ChangePasswordRequest;
import org.ioteatime.meonghanyangserver.user.dto.response.UserDetailResponse;
import org.ioteatime.meonghanyangserver.user.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController implements UserApi {
    private final UserService userService;

    @GetMapping("/{userId}")
    public Api<UserDetailResponse> getUserDetail(@PathVariable("userId") Long userId) {
        UserDetailResponse userDto = userService.getUserDetail(userId);
        return Api.success(AuthSuccessType.GET_USER_DETAIL, userDto);
    }

    @DeleteMapping
    public Api<Object> deleteUser(@LoginMember Long userId) {
        userService.deleteUser(userId);
        return Api.success(AuthSuccessType.DELETE_USER);
    }

    @PutMapping("/password")
    public Api<Object> changeUserPassword(
            @LoginMember Long userId, @RequestBody @Valid ChangePasswordRequest request) {
        userService.changeUserPassword(userId, request);
        return Api.success(AuthSuccessType.UPDATE_PASSWORD);
    }

    @PostMapping("/refresh-token")
    public Api<RefreshResponse> refreshToken(
            @RequestHeader("Authorization") String authorizationHeader) {
        RefreshResponse refreshResponse = userService.reissueAccessToken(authorizationHeader);
        return Api.success(AuthSuccessType.REISSUE_ACCESS_TOKEN, refreshResponse);
    }
}
