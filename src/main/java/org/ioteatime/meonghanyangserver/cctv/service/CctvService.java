package org.ioteatime.meonghanyangserver.cctv.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.ioteatime.meonghanyangserver.cctv.domain.CctvEntity;
import org.ioteatime.meonghanyangserver.cctv.repository.CctvRepository;
import org.ioteatime.meonghanyangserver.clients.kvs.KvsClient;
import org.ioteatime.meonghanyangserver.common.exception.BadRequestException;
import org.ioteatime.meonghanyangserver.common.exception.NotFoundException;
import org.ioteatime.meonghanyangserver.common.type.CctvErrorType;
import org.ioteatime.meonghanyangserver.groupmember.repository.GroupMemberRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CctvService {
    private final KvsClient kvsClient;
    private final CctvRepository cctvRepository;
    private final GroupMemberRepository groupMemberRepository;

    @Transactional
    public void outById(Long memberId, Long cctvId) {
        // Master 권한을 가진 회원만 cctv 퇴출 가능
        if (!groupMemberRepository.isMasterMember(memberId)) {
            throw new BadRequestException(CctvErrorType.ONLY_MASTER_CAN_DELETE);
        }
        CctvEntity cctv =
                cctvRepository
                        .findById(cctvId)
                        .orElseThrow(() -> new NotFoundException(CctvErrorType.NOT_FOUND));
        // 1. KVS 시그널링 채널 삭제
        kvsClient.deleteSignalingChannel(cctv.getKvsChannelName());
        // 2. CCTV 테이블에서 삭제
        cctvRepository.deleteById(cctvId);
    }
}
