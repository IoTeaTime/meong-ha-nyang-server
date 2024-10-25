package org.ioteatime.meonghanyangserver.mail.controller;

import lombok.RequiredArgsConstructor;
import org.ioteatime.meonghanyangserver.common.api.Api;
import org.ioteatime.meonghanyangserver.mail.dto.request.SendRequest;
import org.ioteatime.meonghanyangserver.mail.service.MailService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/open-api/mail")
public class MailController implements MailApi {
    private final MailService mailService;

    @PostMapping("/send")
    public Api<?> send(@RequestBody SendRequest sendRequest) {
        mailService.send(sendRequest.email());
        return Api.OK();
    }
}
