package org.ioteatime.meonghanyangserver.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ioteatime.meonghanyangserver.common.api.Api;
import org.ioteatime.meonghanyangserver.user.dto.CustomUserDetail;
import org.ioteatime.meonghanyangserver.user.dto.request.ChangePasswordRequest;
import org.ioteatime.meonghanyangserver.user.dto.response.UserDetailResponse;
import org.ioteatime.meonghanyangserver.user.service.UserService;
import org.springframework.security.core.Authentication;
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

    @PutMapping("/password")
    public Api<Object> changeUserPassword(
            Authentication authentication, @RequestBody @Valid ChangePasswordRequest request) {

        CustomUserDetail userDetails = (CustomUserDetail) authentication.getPrincipal();
        Long userId = userDetails.getUserEntity().getId();

        userService.changeUserPassword(
                userId, request.getCurrentPassword(), request.getNewPassword());

        return Api.OK();
    }
}
