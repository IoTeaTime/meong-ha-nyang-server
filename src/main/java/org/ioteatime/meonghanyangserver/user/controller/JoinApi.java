package org.ioteatime.meonghanyangserver.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.ioteatime.meonghanyangserver.common.api.Api;
import org.ioteatime.meonghanyangserver.user.dto.UserDto;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Join Api", description = "회원 가입 관련 API 목록입니다.")
public interface JoinApi {
    @Operation(summary = "회원 가입을 합니다.")
    public Api<Object> registerUser(@Valid @RequestBody UserDto userDto);
}
