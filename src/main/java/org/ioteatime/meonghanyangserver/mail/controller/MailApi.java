package org.ioteatime.meonghanyangserver.mail.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.ioteatime.meonghanyangserver.common.api.Api;
import org.ioteatime.meonghanyangserver.mail.dto.request.SendRequest;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "메일")
public interface MailApi {
    @Operation(summary = "메일 전송")
    Api<?> send(@RequestBody SendRequest email);
}
