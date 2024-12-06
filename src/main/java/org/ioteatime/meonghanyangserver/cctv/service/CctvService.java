package org.ioteatime.meonghanyangserver.cctv.service;

import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.ioteatime.meonghanyangserver.cctv.domain.CctvEntity;
import org.ioteatime.meonghanyangserver.cctv.dto.db.CctvWithGroupId;
import org.ioteatime.meonghanyangserver.cctv.dto.request.CreateCctvRequest;
import org.ioteatime.meonghanyangserver.cctv.dto.request.UpdateCctvNickname;
import org.ioteatime.meonghanyangserver.cctv.dto.response.CctvInfoListResponse;
import org.ioteatime.meonghanyangserver.cctv.dto.response.CctvInfoResponse;
import org.ioteatime.meonghanyangserver.cctv.dto.response.CctvSelfInfoResponse;
import org.ioteatime.meonghanyangserver.cctv.dto.response.CreateCctvResponse;
import org.ioteatime.meonghanyangserver.cctv.mapper.CctvResponseMapper;
import org.ioteatime.meonghanyangserver.cctv.repository.CctvRepository;
import org.ioteatime.meonghanyangserver.clients.iot.IotShadowMqttClient;
import org.ioteatime.meonghanyangserver.clients.kvs.KvsClient;
import org.ioteatime.meonghanyangserver.common.exception.BadRequestException;
import org.ioteatime.meonghanyangserver.common.exception.NotFoundException;
import org.ioteatime.meonghanyangserver.common.type.CctvErrorType;
import org.ioteatime.meonghanyangserver.common.type.GroupErrorType;
import org.ioteatime.meonghanyangserver.common.utils.JwtCctvUtils;
import org.ioteatime.meonghanyangserver.common.utils.JwtUtils;
import org.ioteatime.meonghanyangserver.group.domain.GroupEntity;
import org.ioteatime.meonghanyangserver.group.repository.GroupRepository;
import org.ioteatime.meonghanyangserver.groupmember.doamin.GroupMemberEntity;
import org.ioteatime.meonghanyangserver.groupmember.doamin.enums.GroupMemberRole;
import org.ioteatime.meonghanyangserver.groupmember.repository.GroupMemberRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CctvService {
    private final JwtUtils jwtUtils;
    private final KvsClient kvsClient;
    private final JwtCctvUtils jwtCctvUtils;
    private final CctvRepository cctvRepository;
    private final GroupRepository groupRepository;
    private final IotShadowMqttClient iotShadowMqttClient;
    private final GroupMemberRepository groupMemberRepository;

    public CreateCctvResponse createCctv(CreateCctvRequest createCctvRequest) {

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

        CctvEntity cctvEntity = cctvRepository.save(cctv);

        String accessToken =
                jwtUtils.includeBearer(
                        jwtCctvUtils.generateCctvAccessToken(
                                cctvEntity.getCctvNickname(), cctvEntity.getId()));
        // 저장
        return CctvResponseMapper.from(cctvEntity.getId(), accessToken);
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
        // 2. IoT desired -> 삭제되었음을 알림
        iotShadowMqttClient.updateShadow(cctv.getThingId(), "kvsChannelDeleteRequested", true);
        // 3. CCTV 테이블에서 삭제
        cctvRepository.deleteById(cctvId);
    }

    public CctvInfoResponse cctvInfo(Long cctvId) {
        CctvEntity cctvEntity =
                cctvRepository
                        .findByCctvId(cctvId)
                        .orElseThrow(() -> new NotFoundException(CctvErrorType.NOT_FOUND));
        return CctvResponseMapper.from(cctvEntity);
    }

    public CctvSelfInfoResponse cctvSelfInfo(Long cctvId) {
        CctvWithGroupId cctvWithGroupId =
                cctvRepository
                        .findCctvWithGroupIdByCctvId(cctvId)
                        .orElseThrow(() -> new NotFoundException(CctvErrorType.NOT_FOUND));
        return CctvResponseMapper.CctvSelfInfoFrom(cctvWithGroupId);
    }

    public CctvInfoListResponse cctvInfoList(Long memberId, Long groupId) {
        validateMaster(memberId, groupId);
        List<CctvEntity> cctvEntityList = cctvRepository.findByGroupId(groupId);
        List<CctvInfoResponse> cctvInfoResponseList =
                cctvEntityList.stream().map(CctvResponseMapper::from).toList();
        return CctvResponseMapper.from(cctvInfoResponseList);
    }

    @Transactional
    public CctvInfoResponse updateNickname(Long memberId, UpdateCctvNickname request) {
        // CctvId로 cctv 객체 찾기
        CctvEntity cctvEntity =
                cctvRepository
                        .findById(request.cctvId())
                        .orElseThrow(() -> new NotFoundException(CctvErrorType.NOT_FOUND));
        validateMaster(memberId, cctvEntity.getGroup().getId());
        // cctv 이름 변경
        cctvEntity = cctvEntity.updateNickname(request.cctvNickname());
        return CctvResponseMapper.from(cctvEntity);
    }

    private void validateMaster(Long memberId, Long groupId) {
        // GroupId와 memberId로 groupMember가 존재하는지 확인 -> 아니면 에러
        GroupMemberEntity groupMemberEntity =
                groupMemberRepository
                        .findByGroupIdAndMemberId(groupId, memberId)
                        .orElseThrow(
                                () -> new NotFoundException(GroupErrorType.GROUP_MEMBER_NOT_FOUND));
        // Member가 Master인지 확인 -> 아니면 에러
        if (groupMemberEntity.getRole() != GroupMemberRole.ROLE_MASTER) {
            throw new BadRequestException(GroupErrorType.ONLY_MASTER);
        }
    }
}
