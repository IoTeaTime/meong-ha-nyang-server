package org.ioteatime.meonghanyangserver.groupmember.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.ioteatime.meonghanyangserver.cctv.dto.response.CctvInviteResponse;
import org.ioteatime.meonghanyangserver.cctv.repository.CctvRepository;
import org.ioteatime.meonghanyangserver.common.exception.BadRequestException;
import org.ioteatime.meonghanyangserver.common.exception.NotFoundException;
import org.ioteatime.meonghanyangserver.common.type.AuthErrorType;
import org.ioteatime.meonghanyangserver.common.type.GroupErrorType;
import org.ioteatime.meonghanyangserver.common.utils.KvsChannelNameGenerator;
import org.ioteatime.meonghanyangserver.group.domain.GroupEntity;
import org.ioteatime.meonghanyangserver.group.dto.response.GroupResponse;
import org.ioteatime.meonghanyangserver.group.repository.GroupRepository;
import org.ioteatime.meonghanyangserver.groupmember.doamin.GroupMemberEntity;
import org.ioteatime.meonghanyangserver.groupmember.doamin.enums.GroupMemberRole;
import org.ioteatime.meonghanyangserver.groupmember.dto.request.JoinGroupMemberRequest;
import org.ioteatime.meonghanyangserver.groupmember.dto.response.GroupMemberInfoListResponse;
import org.ioteatime.meonghanyangserver.groupmember.dto.response.GroupMemberInfoResponse;
import org.ioteatime.meonghanyangserver.groupmember.dto.response.GroupMemberResponse;
import org.ioteatime.meonghanyangserver.groupmember.mapper.GroupMemberResponseMapper;
import org.ioteatime.meonghanyangserver.groupmember.repository.GroupMemberRepository;
import org.ioteatime.meonghanyangserver.member.domain.MemberEntity;
import org.ioteatime.meonghanyangserver.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GroupMemberService {
    private final CctvRepository cctvRepository;
    private final GroupRepository groupRepository;
    private final MemberRepository memberRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final KvsChannelNameGenerator kvsChannelNameGenerator;

    @Transactional
    public void joinGroupMember(Long memberId, JoinGroupMemberRequest joinGroupMemberRequest) {
        Long groupId = joinGroupMemberRequest.groupId();
        String thingId = joinGroupMemberRequest.thingId();

        if (groupMemberRepository.findByMemberIdAndGroupId(memberId, groupId).isPresent()) {
            throw new BadRequestException(GroupErrorType.GROUP_MEMBER_ALREADY_EXISTS);
        }
        GroupEntity groupEntity =
                groupRepository
                        .findById(groupId)
                        .orElseThrow(() -> new BadRequestException(GroupErrorType.NOT_FOUND));
        MemberEntity memberEntity =
                memberRepository
                        .findById(memberId)
                        .orElseThrow(() -> new BadRequestException(AuthErrorType.NOT_FOUND));

        GroupMemberEntity groupMemberEntity =
                GroupMemberEntity.from(
                        GroupMemberRole.ROLE_PARTICIPANT, thingId, groupEntity, memberEntity);
        groupMemberRepository.save(groupMemberEntity);
    }

    public boolean existsGroupMember(Long memberId) {
        return groupMemberRepository.existsGroupMember(memberId);
    }

    public GroupMemberResponse getGroupMemberInfo(Long memberId) {
        GroupMemberEntity groupMember =
                groupMemberRepository
                        .findByMemberId(memberId)
                        .orElseThrow(
                                () -> new NotFoundException(GroupErrorType.GROUP_MEMBER_NOT_FOUND));

        return new GroupMemberResponse(groupMember.getGroup().getId(), groupMember.getRole());
    }

    public GroupResponse getGroupInfo(Long memberId) {
        GroupMemberEntity groupMember =
                groupMemberRepository
                        .findByMemberId(memberId)
                        .orElseThrow(
                                () -> new NotFoundException(GroupErrorType.GROUP_MEMBER_NOT_FOUND));

        return new GroupResponse(groupMember.getGroup().getId());
    }

    public CctvInviteResponse generateCctvInvite(Long memberId) {
        GroupMemberEntity groupMember =
                groupMemberRepository
                        .findByMemberId(memberId)
                        .orElseThrow(
                                () -> new NotFoundException(GroupErrorType.GROUP_MEMBER_NOT_FOUND));
        String kvsChannelName = kvsChannelNameGenerator.generateUniqueKvsChannelName();

        return new CctvInviteResponse(groupMember.getGroup().getId(), kvsChannelName);
    }

    public GroupEntity getGroup(Long memberId) {
        return groupMemberRepository
                .findGropFromGroupMember(memberId)
                .orElseThrow(() -> new NotFoundException(GroupErrorType.GROUP_MEMBER_NOT_FOUND));
    }

    public void deleteMasterGroupMember(Long memberId, Long groupId, Long groupMemberId) {

        // 그룹 내 방장 확인 권한 확인
        GroupMemberEntity groupMasterEntity =
                groupMemberRepository
                        .findByGroupIdAndMemberIdAndRole(memberId, groupId)
                        .orElseThrow(
                                () ->
                                        new BadRequestException(
                                                GroupErrorType.ONLY_MASTER_REMOVE_GROUP_MEMBER));

        if (groupMasterEntity.getId().equals(groupMemberId)) {
            throw new BadRequestException(GroupErrorType.ONLY_MASTER_REMOVE_GROUP_MASTER);
        }

        groupMemberRepository.deleteById(groupMemberId);
    }

    @Transactional
    public void deleteGroupMember(Long memberId, Long groupId) {
        GroupMemberEntity groupMember =
                groupMemberRepository
                        .findByGroupIdAndMemberId(groupId, memberId)
                        .orElseThrow(
                                () -> new NotFoundException(GroupErrorType.GROUP_MEMBER_NOT_FOUND));

        if (groupMember.getRole().equals(GroupMemberRole.ROLE_MASTER)) {
            // 방장 퇴장
            groupMemberRepository.deleteByGroupId(groupId);
            cctvRepository.deleteByGroupId(groupId);
            groupRepository.deleteById(groupId);
        } else {
            // 참여자 퇴장
            groupMemberRepository.deleteByGroupIdAndMemberId(groupId, memberId);
        }
    }

    public GroupMemberInfoListResponse getGroupMemberInfoList(Long memberId, Long groupId) {
        if (!groupMemberRepository.existsByMemberIdAndGroupIdAndRole(
                memberId, groupId, GroupMemberRole.ROLE_MASTER)) {
            throw new BadRequestException(GroupErrorType.ONLY_MASTER_GET_GROUP_MEMBER_INFO);
        }
        List<GroupMemberEntity> groupMemberEntity =
                groupMemberRepository.findByGroupIdAndRole(
                        groupId, GroupMemberRole.ROLE_PARTICIPANT);
        List<GroupMemberInfoResponse> groupMemberResponseList =
                groupMemberEntity.stream().map(GroupMemberResponseMapper::from).toList();
        return new GroupMemberInfoListResponse(groupMemberResponseList);
    }
}
