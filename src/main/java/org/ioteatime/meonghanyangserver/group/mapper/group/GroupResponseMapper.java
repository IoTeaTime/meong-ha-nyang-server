package org.ioteatime.meonghanyangserver.group.mapper.group;

import org.ioteatime.meonghanyangserver.cctv.dto.response.CctvInfoResponse;
import org.ioteatime.meonghanyangserver.cctv.mapper.CctvResponseMapper;
import org.ioteatime.meonghanyangserver.group.domain.GroupEntity;
import org.ioteatime.meonghanyangserver.group.dto.response.CreateGroupResponse;
import org.ioteatime.meonghanyangserver.group.dto.response.GroupTotalResponse;
import org.ioteatime.meonghanyangserver.group.dto.response.GroupUserInfoResponse;
import org.ioteatime.meonghanyangserver.group.mapper.groupuser.GroupUserResponseMapper;

import java.util.List;
import java.util.stream.Collectors;

public class GroupResponseMapper {
    public static CreateGroupResponse toResponse(GroupEntity groupEntity) {
        return new CreateGroupResponse(
                groupEntity.getId(), groupEntity.getGroupName(), groupEntity.getCreatedAt());
    }

    public static GroupTotalResponse toGroupTotalResponse(GroupEntity groupEntity){
        List<GroupUserInfoResponse> groupUserInfoResponseList = groupEntity.getGroupUserEntities().stream()
                .map(GroupUserResponseMapper::toResponse).toList();

        List<CctvInfoResponse> cctvInfoResponseList = groupEntity.getCctvs().stream()
                .map(CctvResponseMapper::toCctvInfoResponse).toList();

        return new GroupTotalResponse(
                groupEntity.getId(),
                groupEntity.getGroupName(),
                groupEntity.getCreatedAt(),
                groupUserInfoResponseList,
                cctvInfoResponseList
        );
    }
}
