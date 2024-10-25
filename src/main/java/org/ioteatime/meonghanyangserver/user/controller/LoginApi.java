package org.ioteatime.meonghanyangserver.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.ioteatime.meonghanyangserver.common.api.Api;
import org.ioteatime.meonghanyangserver.user.dto.request.LoginDto;
import org.ioteatime.meonghanyangserver.user.dto.response.LoginResponse;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Login Api", description = "로그인 API 목록입니다.")
public interface LoginApi {
    @Operation(summary = "로그인 api 입니다.")
    Api<LoginResponse> login(
            @RequestBody
            @Valid
            LoginDto loginDto
    );

}
