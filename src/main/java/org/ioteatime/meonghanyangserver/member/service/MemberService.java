package org.ioteatime.meonghanyangserver.member.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.ioteatime.meonghanyangserver.auth.dto.reponse.RefreshResponse;
import org.ioteatime.meonghanyangserver.auth.mapper.AuthResponseMapper;
import org.ioteatime.meonghanyangserver.cctv.domain.CctvEntity;
import org.ioteatime.meonghanyangserver.cctv.repository.CctvRepository;
import org.ioteatime.meonghanyangserver.clients.iot.IotShadowMqttClient;
import org.ioteatime.meonghanyangserver.clients.kvs.KvsClient;
import org.ioteatime.meonghanyangserver.common.exception.BadRequestException;
import org.ioteatime.meonghanyangserver.common.exception.NotFoundException;
import org.ioteatime.meonghanyangserver.common.type.AuthErrorType;
import org.ioteatime.meonghanyangserver.common.type.GroupErrorType;
import org.ioteatime.meonghanyangserver.common.utils.JwtUtils;
import org.ioteatime.meonghanyangserver.group.domain.GroupEntity;
import org.ioteatime.meonghanyangserver.group.dto.response.UpdateNicknameAndGroupNameResponse;
import org.ioteatime.meonghanyangserver.group.mapper.GroupResponseMapper;
import org.ioteatime.meonghanyangserver.group.repository.GroupRepository;
import org.ioteatime.meonghanyangserver.groupmember.doamin.GroupMemberEntity;
import org.ioteatime.meonghanyangserver.groupmember.doamin.enums.GroupMemberRole;
import org.ioteatime.meonghanyangserver.groupmember.repository.GroupMemberRepository;
import org.ioteatime.meonghanyangserver.member.domain.MemberEntity;
import org.ioteatime.meonghanyangserver.member.dto.request.ChangePasswordRequest;
import org.ioteatime.meonghanyangserver.member.dto.request.UpdateNicknameAndGroupNameRequest;
import org.ioteatime.meonghanyangserver.member.dto.response.MemberDetailResponse;
import org.ioteatime.meonghanyangserver.member.mapper.MemberResponseMapper;
import org.ioteatime.meonghanyangserver.member.repository.MemberRepository;
import org.ioteatime.meonghanyangserver.redis.AccessTokenRepository;
import org.ioteatime.meonghanyangserver.redis.RefreshToken;
import org.ioteatime.meonghanyangserver.redis.RefreshTokenRepository;
import org.ioteatime.meonghanyangserver.video.repository.VideoRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.amazon.awssdk.iot.iotshadow.IotShadowClient;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final JwtUtils jwtUtils;
    private final KvsClient kvsClient;
    private final CctvRepository cctvRepository;
    private final GroupRepository groupRepository;
    private final IotShadowClient iotShadowClient;
    private final VideoRepository videoRepository;
    private final MemberRepository memberRepository;
    private final IotShadowMqttClient iotShadowMqttClient;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final GroupMemberRepository groupMemberRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final AccessTokenRepository accessTokenRepository;

    public MemberDetailResponse getMemberDetail(Long memberId) {
        MemberEntity memberEntity =
                memberRepository
                        .findById(memberId)
                        .orElseThrow(() -> new NotFoundException(AuthErrorType.NOT_FOUND));

        return MemberResponseMapper.from(memberEntity);
    }

    // NOTE.
    // 1. Master는 권한을 위임하지 않는다. -> Master가 탈퇴하면 그룹이 삭제된다.
    // 2. 회원은 하나의 그룹에만 소속된다.
    @Transactional
    public void deleteMember(Long memberId) {
        // MemberId 기준으로 GroupMember 조회
        GroupMemberEntity groupMemberEntity =
                groupMemberRepository
                        .findByMemberId(memberId)
                        .orElseThrow(
                                () -> new NotFoundException(GroupErrorType.GROUP_MEMBER_NOT_FOUND));
        // 탈퇴하는 사람이 MASTER이면 Group을 삭제
        if (groupMemberEntity.getRole() == GroupMemberRole.ROLE_MASTER) {
            // MemberId 기준으로 GroupMember 통해 GroupId 조회
            Long groupId = groupMemberEntity.getGroup().getId();
            // GroupId 기준으로 video 조회하여 삭제 (S3 배치 작업 추가 필요)
            videoRepository.deleteAllByGroupId(groupId);

            // KVS 채널 삭제는 rollback이 어려우므로 최대한 마지막에 처리
            // GroupId 기준으로 cctv 조회하여 정보 목록 확인
            List<CctvEntity> cctvEntities = cctvRepository.findByGroupId(groupId);
            // CCTV 목록을 순회하여 KVS 채널 삭제
            cctvEntities.forEach(
                    cctv -> {
                        kvsClient.deleteSignalingChannel(cctv.getKvsChannelName());

                        // CCTV 상태 shadow에서 kvsChannelDeleteRequested 필드 true로 pub -> (iot 기기 삭제는
                        // 모바일에서)
                        iotShadowMqttClient.updateShadow(
                                cctv.getThingId(), "kvsChannelDeleteRequested", true);
                    });
            // CCTV 목록 삭제
            cctvRepository.deleteByGroupId(groupId);

            // GroupMember 삭제
            groupMemberRepository.deleteById(groupMemberEntity.getId());

            // group 삭제
            groupRepository.deleteById(groupId);
        } else {
            // GroupMember 삭제만 진행 (PARTICIPANT인 경우)
            groupMemberRepository.deleteById(groupMemberEntity.getId());
        }
        // member 삭제 (PARTICIPANT도 해당)
        memberRepository.deleteById(memberId);
    }

    @Transactional
    public void changeMemberPassword(Long memberId, ChangePasswordRequest request) {
        String currentPassword = request.currentPassword();
        String newPassword = request.newPassword();

        MemberEntity memberEntity =
                memberRepository
                        .findById(memberId)
                        .orElseThrow(() -> new NotFoundException(AuthErrorType.NOT_FOUND));

        if (!bCryptPasswordEncoder.matches(currentPassword, memberEntity.getPassword())) {
            throw new BadRequestException(AuthErrorType.PASSWORD_NOT_MATCH);
        }

        // Dirty-Checking Password Change
        memberEntity.setPassword(bCryptPasswordEncoder.encode(newPassword));
    }

    public RefreshResponse reissueAccessToken(String authorizationHeader) {
        String refreshToken = jwtUtils.extractTokenFromHeader(authorizationHeader);

        Long memberId = jwtUtils.getIdFromToken(refreshToken);
        MemberEntity memberEntity =
                memberRepository
                        .findById(memberId)
                        .orElseThrow(() -> new NotFoundException(AuthErrorType.NOT_FOUND));

        if (!jwtUtils.validateToken(refreshToken, memberEntity)) {
            throw new NotFoundException(AuthErrorType.REFRESH_TOKEN_INVALID);
        }

        RefreshToken storedToken =
                refreshTokenRepository
                        .findByRefreshToken(refreshToken)
                        .orElseThrow(
                                () -> new NotFoundException(AuthErrorType.REFRESH_TOKEN_NOT_FOUND));

        if (!storedToken.getRefreshToken().equals(refreshToken)) {
            throw new BadRequestException(AuthErrorType.TOKEN_NOT_EQUALS);
        }

        String newAccessToken = jwtUtils.generateAccessToken(memberEntity.getNickname(), memberId);
        newAccessToken = jwtUtils.includeBearer(newAccessToken);

        return AuthResponseMapper.from(newAccessToken);
    }

    @Transactional
    public UpdateNicknameAndGroupNameResponse updateNicknameAndGroupName(
            Long memberId, UpdateNicknameAndGroupNameRequest request) {
        String updatedNickname = null, updatedGroupName = null;
        if (request.nickname() != null) {
            MemberEntity memberEntity =
                    memberRepository
                            .findById(memberId)
                            .orElseThrow(() -> new NotFoundException(AuthErrorType.NOT_FOUND));
            memberEntity = memberEntity.updateNickname(request.nickname());
            updatedNickname = memberEntity.getNickname();
        }
        if (request.groupName() != null) {
            GroupMemberEntity groupMemberEntity =
                    groupMemberRepository
                            .findByMemberId(memberId)
                            .orElseThrow(
                                    () ->
                                            new NotFoundException(
                                                    GroupErrorType.GROUP_MEMBER_NOT_FOUND));
            GroupEntity group = groupMemberEntity.getGroup();
            group = group.updateGroupName(request.groupName());
            updatedGroupName = group.getGroupName();
        }
        return GroupResponseMapper.from(updatedNickname, updatedGroupName);
    }
}
