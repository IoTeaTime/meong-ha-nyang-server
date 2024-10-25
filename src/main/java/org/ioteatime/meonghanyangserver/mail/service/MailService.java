package org.ioteatime.meonghanyangserver.mail.service;

import lombok.RequiredArgsConstructor;
import org.ioteatime.meonghanyangserver.clients.google.GoogleMailClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {
    private final GoogleMailClient googleMailClient;

    public void send(String email) {
        // TODO. Redis 적용 후 코드 발급 구현 필요
        googleMailClient.sendMail(email, "hello", "world");
    }
}
