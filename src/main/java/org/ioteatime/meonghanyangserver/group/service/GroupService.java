package org.ioteatime.meonghanyangserver.group.service;

import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.ioteatime.meonghanyangserver.common.exception.BadRequestException;
import org.ioteatime.meonghanyangserver.common.exception.NotFoundException;
import org.ioteatime.meonghanyangserver.common.type.AuthErrorType;
import org.ioteatime.meonghanyangserver.common.type.GroupErrorType;
import org.ioteatime.meonghanyangserver.group.domain.GroupEntity;
import org.ioteatime.meonghanyangserver.group.dto.response.CreateGroupResponse;
import org.ioteatime.meonghanyangserver.group.dto.response.GroupTotalResponse;
import org.ioteatime.meonghanyangserver.group.mapper.GroupEntityMapper;
import org.ioteatime.meonghanyangserver.group.mapper.GroupResponseMapper;
import org.ioteatime.meonghanyangserver.group.repository.GroupRepository;
import org.ioteatime.meonghanyangserver.groupmember.doamin.enums.GroupMemberRole;
import org.ioteatime.meonghanyangserver.groupmember.repository.JpaGroupMemberRepository;
import org.ioteatime.meonghanyangserver.groupmember.service.GroupMemberService;
import org.ioteatime.meonghanyangserver.member.domain.MemberEntity;
import org.ioteatime.meonghanyangserver.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;
    private final GroupMemberService groupMemberService;
    private final MemberRepository memberRepository;
    private final JpaGroupMemberRepository groupUserRepository;

    // create group
    @Transactional
    public CreateGroupResponse createGroup(Long memberId) {

        boolean groupUserEntity = groupMemberService.existsGroupMember(memberId);

        if (groupUserEntity) {
            throw new BadRequestException(GroupErrorType.ALREADY_EXISTS);
        }

        MemberEntity memberEntity =
                memberRepository
                        .findById(memberId)
                        .orElseThrow(() -> new NotFoundException(AuthErrorType.NOT_FOUND));

        String roomName = memberEntity.getNickname() + " 그룹";

        GroupEntity groupEntity = GroupEntityMapper.toEntity(roomName);

        GroupEntity newGroupEntity = groupRepository.save(groupEntity);
        // TODO iot core 연결 완료시 thing id 추가
        groupMemberService.createGroupMember(
                newGroupEntity, memberEntity, GroupMemberRole.ROLE_MASTER, "thing id");

        return GroupResponseMapper.from(newGroupEntity);
    }

    public GroupTotalResponse getGroupTotalData(Long memberId) {
        GroupEntity groupEntity =
                Optional.ofNullable(groupMemberService.getGroup(memberId))
                        .orElseThrow(
                                () -> new NotFoundException(GroupErrorType.GROUP_MEMBER_NOT_FOUND));

        return GroupResponseMapper.fromGroupTotal(groupEntity);
    }
}
