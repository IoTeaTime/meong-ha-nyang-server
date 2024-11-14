package org.ioteatime.meonghanyangserver.fcm.service;

import org.ioteatime.meonghanyangserver.clients.google.FcmClient;
import org.springframework.stereotype.Service;

@Service
public class FcmService {
    private final FcmClient fcmClient;

    public FcmService(FcmClient fcmClient) {
        this.fcmClient = fcmClient;
        fcmClient.init();
    }

    public void saveToken(String token) {
        // TODO. 토큰을 DB에 저장하기
        // TODO. 로그인 계정 정보 불러오는 방식으로 구현하기
        // 현재는 테스트를 위해 token이 자동으로 hello Topic을 구독하고, 곧바로 hello Topic에 메시지를 보냄
        fcmClient.subTopic(token, "hello");
        fcmClient.sendPush("test-message", "test-content", "hello");
    }
}
