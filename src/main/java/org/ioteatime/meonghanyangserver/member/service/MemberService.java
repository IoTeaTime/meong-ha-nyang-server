package org.ioteatime.meonghanyangserver.member.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import org.ioteatime.meonghanyangserver.image.repository.ImageRepository;
import org.ioteatime.meonghanyangserver.member.domain.MemberEntity;
import org.ioteatime.meonghanyangserver.member.dto.request.ChangePasswordRequest;
import org.ioteatime.meonghanyangserver.member.dto.request.UpdateNicknameAndGroupNameRequest;
import org.ioteatime.meonghanyangserver.member.dto.response.MemberWithGroupDetailResponse;
import org.ioteatime.meonghanyangserver.member.mapper.MemberResponseMapper;
import org.ioteatime.meonghanyangserver.member.repository.MemberRepository;
import org.ioteatime.meonghanyangserver.redis.AccessToken;
import org.ioteatime.meonghanyangserver.redis.AccessTokenRepository;
import org.ioteatime.meonghanyangserver.redis.RefreshToken;
import org.ioteatime.meonghanyangserver.redis.RefreshTokenRepository;
import org.ioteatime.meonghanyangserver.video.repository.VideoRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.amazon.awssdk.iot.iotshadow.IotShadowClient;

@Slf4j
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
    private final ImageRepository imageRepository;

    public MemberWithGroupDetailResponse getMemberDetail(Long memberId) {
        MemberEntity memberEntity =
                memberRepository
                        .findById(memberId)
                        .orElseThrow(() -> new NotFoundException(AuthErrorType.NOT_FOUND));

        Optional<GroupMemberEntity> groupMemberEntity =
                groupMemberRepository.findByMemberId(memberId);
        GroupEntity groupEntity = null;
        if (groupMemberEntity.isPresent()) {
            groupEntity = groupMemberEntity.get().getGroup();
        }

        return MemberResponseMapper.from(memberEntity, groupEntity);
    }

    // NOTE.
    // 1. Master는 권한을 위임하지 않는다. -> Master가 탈퇴하면 그룹이 삭제된다.
    // 2. 회원은 하나의 그룹에만 소속된다.
    @Transactional
    public void deleteMember(String authHeader, Long memberId) {
        log.info(
                "[회원 탈퇴] {}",
                "탈퇴 작업을 시작합니다. AuthHeader : " + authHeader + " memberId : " + memberId);
        // MemberId 기준으로 GroupMember 조회
        Optional<GroupMemberEntity> groupMemberOptionalEntity =
                groupMemberRepository.findByMemberId(memberId);

        // Redis에서 refreshToken을 삭제하고 accessToken을 블랙리스트로 저장
        deleteMemberToken(authHeader, memberId);
        log.info("[회원 탈퇴] {}", "Redis에서 refreshToken을 삭제하고 accessToken을 블랙리스트로 저장하였습니다.");

        // GroupMember가 없으면 == 가입된 그룹이 없으면 바로 탈퇴 진행
        if (groupMemberOptionalEntity.isEmpty()) {
            memberRepository.deleteById(memberId);
            log.info("[회원 탈퇴] {}", "가입된 그룹이 없어 바로 탈퇴를 진행하였습니다.");
            return;
        }

        GroupMemberEntity groupMemberEntity = groupMemberOptionalEntity.get();

        Long groupMemberId = groupMemberEntity.getId();
        Long groupId = groupMemberEntity.getGroup().getId();
        GroupMemberRole groupMemberRole = groupMemberEntity.getRole();

        // 탈퇴하는 사람이 MASTER이면 Group을 삭제
        if (groupMemberRole == GroupMemberRole.ROLE_MASTER) {
            // GroupId 기준으로 video 조회하여 삭제 (S3 배치 작업 추가 필요)
            videoRepository.deleteAllByGroupId(groupId);
            log.info("[방장 회원 탈퇴] {}", "GroupId 기준으로 video 조회하여 삭제하였습니다.");

            // GroupId 기준으로 cctv 조회하여 정보 목록 확인
            List<CctvEntity> cctvEntities = cctvRepository.findByGroupId(groupId);

            // CCTV 목록 삭제
            cctvRepository.deleteByGroupId(groupId);
            log.info("[방장 회원 탈퇴] {}", "GroupId 기준으로 CCTV 목록을 삭제하였습니다.");

            // Image 목록 group 필드 null
            imageRepository.updateGroupNull(groupId);
            log.info("[방장 회원 탈퇴] {}", "GroupId 기준으로 Image 목록의 Group 필드를 Null로 변경하였습니다.");

            // GroupId 기준으로 groupMember 모두 찾아 삭제
            groupMemberRepository.deleteAllByGroupId(groupId);
            log.info("[방장 회원 탈퇴] {}", "GroupId 기준으로 GroupMember를 모두 삭제하였습니다.");

            // Group 삭제
            groupRepository.deleteById(groupId);
            log.info("[방장 회원 탈퇴] {}", "GroupId 기준으로 Group을 삭제하였습니다.");

            // KVS 채널 삭제는 rollback이 어려우므로 최대한 마지막에 처리
            // CCTV 목록을 순회하여 KVS 채널 삭제
            cctvEntities.forEach(
                    cctv -> {
                        kvsClient.deleteSignalingChannel(cctv.getKvsChannelName());

                        // CCTV 상태 shadow에서 kvsChannelDeleteRequested 필드 true로 pub -> (iot 기기 삭제는
                        // 모바일에서)
                        iotShadowMqttClient.updateShadow(
                                cctv.getThingId(), "kvsChannelDeleteRequested", true);
                    });
            log.info("[방장 회원 탈퇴] {}", "CCTV의 KVS 채널을 모두 삭제하였습니다.");
        } else {
            // GroupMember 삭제만 진행 (PARTICIPANT인 경우)
            groupMemberRepository.deleteById(groupMemberId);
            log.info("[참여자 회원 탈퇴] {}", "GroupId 기준으로 GroupMember를 모두 삭제하였습니다.");
        }

        // Member 삭제 (PARTICIPANT도 해당)
        memberRepository.deleteById(memberId);
        log.info("[회원 탈퇴] {}", "Member를 삭제하였습니다.");
    }

    private void deleteMemberToken(String authHeader, Long memberId) {
        refreshTokenRepository.deleteByMemberId(memberId);
        authHeader = jwtUtils.extractTokenFromHeader(authHeader);
        Date date = jwtUtils.getExpirationDateFromToken(authHeader);
        // access token의 남은 시간
        long ttl = (date.getTime() - System.currentTimeMillis()) / 1000;

        AccessToken accessTokenEntity =
                AccessToken.builder().accessToken(authHeader).ttl(ttl).build();
        // access token 블랙리스트
        accessTokenRepository.save(accessTokenEntity);
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
