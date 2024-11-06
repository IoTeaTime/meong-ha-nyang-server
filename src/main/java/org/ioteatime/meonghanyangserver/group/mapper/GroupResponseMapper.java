package org.ioteatime.meonghanyangserver.group.mapper;

import org.ioteatime.meonghanyangserver.group.domain.GroupEntity;
import org.ioteatime.meonghanyangserver.group.dto.response.CreateGroupResponse;
import org.ioteatime.meonghanyangserver.group.dto.response.GroupTotalResponse;

public class GroupResponseMapper {
    public static CreateGroupResponse from(GroupEntity groupEntity) {
        return new CreateGroupResponse(
                groupEntity.getId(), groupEntity.getGroupName(), groupEntity.getCreatedAt());
    }

    public static GroupTotalResponse fromGroupTotal(GroupEntity groupEntity) {
        //        List<GroupMemberInfoResponse> deviceInfoResponseList =
        //
        // groupEntity.getDeviceEntities().stream().map(GroupMemberResponseMapper::from).toList();
        //
        //        List<CctvInfoResponse> cctvInfoResponseList =
        //                groupEntity.getDeviceEntities().stream()
        //                        .map(it -> it.getCctv())
        //                        .filter(Objects::nonNull)
        //                        .map(CctvResponseMapper::from)
        //                        .toList();
        //
        //        return new GroupTotalResponse(
        //                groupEntity.getId(),
        //                groupEntity.getGroupName(),
        //                groupEntity.getCreatedAt(),
        //                deviceInfoResponseList,
        //                cctvInfoResponseList);
        return null;
    }
}
