package org.ioteatime.meonghanyangserver.fcm.service;

import jakarta.transaction.Transactional;
import org.ioteatime.meonghanyangserver.clients.google.FcmClient;
import org.ioteatime.meonghanyangserver.common.exception.NotFoundException;
import org.ioteatime.meonghanyangserver.common.type.AuthErrorType;
import org.ioteatime.meonghanyangserver.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class FcmService {
    private final FcmClient fcmClient;
    private final MemberRepository memberRepository;

    public FcmService(FcmClient fcmClient, MemberRepository memberRepository) {
        this.fcmClient = fcmClient;
        this.memberRepository = memberRepository;
        fcmClient.init();
    }

    @Transactional
    public void saveToken(Long memberId, String token) {
        memberRepository
                .updateFcmTokenById(memberId, token)
                .orElseThrow(() -> new NotFoundException(AuthErrorType.NOT_FOUND));
    }
}
