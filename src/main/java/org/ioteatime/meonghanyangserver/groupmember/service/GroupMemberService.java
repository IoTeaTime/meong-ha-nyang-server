package org.ioteatime.meonghanyangserver.groupmember.service;

import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.ioteatime.meonghanyangserver.cctv.dto.response.CctvInviteResponse;
import org.ioteatime.meonghanyangserver.common.exception.BadRequestException;
import org.ioteatime.meonghanyangserver.common.exception.NotFoundException;
import org.ioteatime.meonghanyangserver.common.type.GroupErrorType;
import org.ioteatime.meonghanyangserver.common.utils.KvsChannelNameGenerator;
import org.ioteatime.meonghanyangserver.group.domain.GroupEntity;
import org.ioteatime.meonghanyangserver.group.dto.response.GroupInfoResponse;
import org.ioteatime.meonghanyangserver.groupmember.doamin.GroupMemberEntity;
import org.ioteatime.meonghanyangserver.groupmember.doamin.enums.GroupMemberRole;
import org.ioteatime.meonghanyangserver.groupmember.mapper.GroupMemberEntityMapper;
import org.ioteatime.meonghanyangserver.groupmember.repository.GroupMemberRepository;
import org.ioteatime.meonghanyangserver.member.domain.MemberEntity;
import org.ioteatime.meonghanyangserver.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupMemberService {
    private final GroupMemberRepository groupMemberRepository;
    private final KvsChannelNameGenerator kvsChannelNameGenerator;
    private final MemberRepository memberRepository;

    // input user
    public void createGroupMember(
            GroupEntity groupEntity,
            MemberEntity memberEntity,
            GroupMemberRole groupMemberRole,
            String thingId) {
        GroupMemberEntity groupMember =
                GroupMemberEntityMapper.from(groupEntity, memberEntity, groupMemberRole, thingId);
        groupMemberRepository.createGroupMember(groupMember);
    }

    public boolean existsGroupMember(Long memberId) {
        return groupMemberRepository.existsGroupMember(memberId);
    }

    public GroupInfoResponse getUserGroupInfo(Long memberId) {
        GroupMemberEntity groupMember =
                groupMemberRepository
                        .findByMemberId(memberId)
                        .orElseThrow(
                                () -> new NotFoundException(GroupErrorType.GROUP_MEMBER_NOT_FOUND));

        return new GroupInfoResponse(groupMember.getGroup().getId());
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
        return Optional.ofNullable(groupMemberRepository.findGroupMember(memberId))
                .orElseThrow(() -> new NotFoundException(GroupErrorType.GROUP_MEMBER_NOT_FOUND));
    }

    public void deleteMasterGroupMember(Long memberId, Long groupId, Long groupMemberId) {

        // 그룹 내 방장 확인 권한 확인
        GroupMemberEntity groupMasterEntity =
                Optional.ofNullable(
                                groupMemberRepository.findByGroupIdAndMemberIdAndRole(
                                        memberId, groupId))
                        .orElseThrow(
                                () ->
                                        new BadRequestException(
                                                GroupErrorType.ONLY_MASTER_REMOVE_GROUP_MEMBER));

        if (Objects.equals(groupMasterEntity.getId(), groupMemberId)) {
            throw new BadRequestException(GroupErrorType.ONLY_MASTER_REMOVE_GROUP_MASTER);
        }

        //TODO group member iot core 제외

        groupMemberRepository.deleteById(groupMemberId);
    }
}
