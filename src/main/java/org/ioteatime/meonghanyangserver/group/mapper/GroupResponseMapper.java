package org.ioteatime.meonghanyangserver.group.mapper;

import java.util.List;
import org.ioteatime.meonghanyangserver.cctv.dto.response.CctvInfoResponse;
import org.ioteatime.meonghanyangserver.cctv.mapper.CctvResponseMapper;
import org.ioteatime.meonghanyangserver.group.domain.GroupEntity;
import org.ioteatime.meonghanyangserver.group.dto.response.CreateGroupResponse;
import org.ioteatime.meonghanyangserver.group.dto.response.GroupTotalResponse;
import org.ioteatime.meonghanyangserver.group.dto.response.UpdateNicknameAndGroupNameResponse;
import org.ioteatime.meonghanyangserver.groupmember.dto.response.GroupMemberInfoResponse;
import org.ioteatime.meonghanyangserver.groupmember.mapper.GroupMemberResponseMapper;

public class GroupResponseMapper {
    public static CreateGroupResponse from(GroupEntity groupEntity) {
        return new CreateGroupResponse(
                groupEntity.getId(), groupEntity.getGroupName(), groupEntity.getCreatedAt());
    }

    public static GroupTotalResponse fromGroupTotal(GroupEntity groupEntity) {
        List<GroupMemberInfoResponse> deviceInfoResponseList =
                groupEntity.getGroupMemberEntities().stream()
                        .map(GroupMemberResponseMapper::from)
                        .toList();

        List<CctvInfoResponse> cctvInfoResponseList =
                groupEntity.getCctvEntities().stream().map(CctvResponseMapper::from).toList();

        return new GroupTotalResponse(
                groupEntity.getId(),
                groupEntity.getGroupName(),
                groupEntity.getCreatedAt(),
                deviceInfoResponseList,
                cctvInfoResponseList);
    }

    public static UpdateNicknameAndGroupNameResponse from(String nickname, String groupName) {
        return new UpdateNicknameAndGroupNameResponse(nickname, groupName);
    }
}
