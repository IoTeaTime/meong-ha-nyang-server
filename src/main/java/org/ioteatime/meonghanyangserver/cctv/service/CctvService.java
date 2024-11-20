package org.ioteatime.meonghanyangserver.cctv.service;

import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.ioteatime.meonghanyangserver.cctv.domain.CctvEntity;
import org.ioteatime.meonghanyangserver.cctv.dto.request.CreateCctvRequest;
import org.ioteatime.meonghanyangserver.cctv.dto.response.CctvInfoListResponse;
import org.ioteatime.meonghanyangserver.cctv.dto.response.CctvInfoResponse;
import org.ioteatime.meonghanyangserver.cctv.mapper.CctvResponseMapper;
import org.ioteatime.meonghanyangserver.cctv.repository.CctvRepository;
import org.ioteatime.meonghanyangserver.clients.kvs.KvsClient;
import org.ioteatime.meonghanyangserver.common.exception.BadRequestException;
import org.ioteatime.meonghanyangserver.common.exception.NotFoundException;
import org.ioteatime.meonghanyangserver.common.type.CctvErrorType;
import org.ioteatime.meonghanyangserver.common.type.GroupErrorType;
import org.ioteatime.meonghanyangserver.group.domain.GroupEntity;
import org.ioteatime.meonghanyangserver.group.repository.GroupRepository;
import org.ioteatime.meonghanyangserver.groupmember.repository.GroupMemberRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CctvService {
    private final KvsClient kvsClient;
    private final CctvRepository cctvRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final GroupRepository groupRepository;

    public void createCctv(CreateCctvRequest createCctvRequest) {

        if (cctvRepository.existsByThingId(createCctvRequest.thingId())) {
            throw new BadRequestException(CctvErrorType.ALREADY_EXIST);
        }
        GroupEntity groupEntity =
                groupRepository
                        .findById(createCctvRequest.groupId())
                        .orElseThrow(() -> new NotFoundException((GroupErrorType.NOT_FOUND)));

        String cctvNickname = "CCTV-" + createCctvRequest.thingId().substring(1, 3);

        // CctvEntity 객체 생성
        CctvEntity cctv =
                CctvEntity.builder()
                        .cctvNickname(cctvNickname)
                        .kvsChannelName(createCctvRequest.kvsChannelName())
                        .thingId(createCctvRequest.thingId())
                        .group(groupEntity)
                        .build();

        // 저장
        cctvRepository.save(cctv);
    }

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

    public CctvInfoResponse cctvInfo(String thingId) {
        CctvEntity cctvEntity =
                cctvRepository
                        .findByThingId(thingId)
                        .orElseThrow(() -> new BadRequestException(CctvErrorType.NOT_FOUND));
        return CctvResponseMapper.from(cctvEntity);
    }

    public CctvInfoListResponse cctvInfoList(Long memberId, Long groupId) {
        if (!groupMemberRepository.existsByMemberIdAndGroupId(memberId, groupId)) {
            throw new BadRequestException(GroupErrorType.GROUP_MEMBER_NOT_FOUND);
        }
        List<CctvEntity> cctvEntityList = cctvRepository.findByGroupId(groupId);
        List<CctvInfoResponse> cctvInfoResponseList =
                cctvEntityList.stream().map(CctvResponseMapper::from).toList();
        CctvInfoListResponse cctvInfoListResponse = new CctvInfoListResponse(cctvInfoResponseList);
        return cctvInfoListResponse;
    }
}
