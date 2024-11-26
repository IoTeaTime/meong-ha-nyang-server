package org.ioteatime.meonghanyangserver.group.mapper;

import java.util.List;
import org.ioteatime.meonghanyangserver.cctv.dto.response.CctvInfoResponse;
import org.ioteatime.meonghanyangserver.cctv.mapper.CctvResponseMapper;
import org.ioteatime.meonghanyangserver.group.domain.GroupEntity;
import org.ioteatime.meonghanyangserver.group.dto.response.CreateGroupResponse;
import org.ioteatime.meonghanyangserver.group.dto.response.GroupTotalResponse;
import org.ioteatime.meonghanyangserver.group.dto.response.UpdateNicknameAndGroupNameResponse;

public class GroupResponseMapper {
    public static CreateGroupResponse from(GroupEntity groupEntity) {
        return new CreateGroupResponse(
                groupEntity.getId(), groupEntity.getGroupName(), groupEntity.getCreatedAt());
    }

    public static GroupTotalResponse fromGroupTotal(GroupEntity groupEntity) {
        List<CctvInfoResponse> cctvInfoResponseList =
                groupEntity.getCctvEntities().stream().map(CctvResponseMapper::from).toList();

        return new GroupTotalResponse(
                groupEntity.getId(),
                groupEntity.getGroupName(),
                groupEntity.getCreatedAt(),
                cctvInfoResponseList);
    }

    public static UpdateNicknameAndGroupNameResponse from(String nickname, String groupName) {
        return new UpdateNicknameAndGroupNameResponse(nickname, groupName);
    }
}
