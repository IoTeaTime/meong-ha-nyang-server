package org.ioteatime.meonghanyangserver.fcm.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.ioteatime.meonghanyangserver.clients.google.FcmClient;
import org.ioteatime.meonghanyangserver.common.exception.NotFoundException;
import org.ioteatime.meonghanyangserver.common.type.AuthErrorType;
import org.ioteatime.meonghanyangserver.common.type.GroupErrorType;
import org.ioteatime.meonghanyangserver.fcm.dto.response.FcmTopicResponse;
import org.ioteatime.meonghanyangserver.fcm.mapper.FcmMapper;
import org.ioteatime.meonghanyangserver.group.domain.GroupEntity;
import org.ioteatime.meonghanyangserver.groupmember.repository.GroupMemberRepository;
import org.ioteatime.meonghanyangserver.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FcmService {
    private final FcmClient fcmClient;
    private final MemberRepository memberRepository;
    private final GroupMemberRepository groupMemberRepository;

    @Transactional
    public void saveToken(Long memberId, String token) {
        memberRepository
                .updateFcmTokenById(memberId, token)
                .orElseThrow(() -> new NotFoundException(AuthErrorType.NOT_FOUND));
        // 토픽이 갱신될 때마다 토픽 다시 구독
        GroupEntity group =
                groupMemberRepository
                        .findGroupFromGroupMember(memberId)
                        .orElseThrow(() -> new NotFoundException(GroupErrorType.NOT_FOUND));
        fcmClient.subTopic(token, group.getFcmTopic());
    }

    public FcmTopicResponse findFcmTopicByGroupId(Long memberId) {
        GroupEntity group =
                groupMemberRepository
                        .findGroupFromGroupMember(memberId)
                        .orElseThrow(() -> new NotFoundException(GroupErrorType.NOT_FOUND));
        return FcmMapper.from(group.getFcmTopic());
    }
}
