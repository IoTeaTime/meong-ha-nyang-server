package org.ioteatime.meonghanyangserver.group.mapper;

import java.util.List;
import java.util.Objects;

import org.ioteatime.meonghanyangserver.cctv.dto.response.CctvInfoResponse;
import org.ioteatime.meonghanyangserver.cctv.mapper.CctvResponseMapper;
import org.ioteatime.meonghanyangserver.group.domain.GroupEntity;
import org.ioteatime.meonghanyangserver.group.dto.response.CreateGroupResponse;
import org.ioteatime.meonghanyangserver.group.dto.response.GroupTotalResponse;
import org.ioteatime.meonghanyangserver.device.dto.response.DeviceInfoResponse;
import org.ioteatime.meonghanyangserver.device.mapper.DeviceResponseMapper;

public class GroupResponseMapper {
    public static CreateGroupResponse from(GroupEntity groupEntity) {
        return new CreateGroupResponse(
                groupEntity.getId(), groupEntity.getGroupName(), groupEntity.getCreatedAt());
    }

    public static GroupTotalResponse fromGroupTotal(GroupEntity groupEntity) {
        List<DeviceInfoResponse> deviceInfoResponseList =
                groupEntity.getDeviceEntities().stream()
                        .map(DeviceResponseMapper::from)
                        .toList();

        List<CctvInfoResponse> cctvInfoResponseList = groupEntity.getDeviceEntities()
                .stream()
                .map(it -> it.getCctv())
                .filter(Objects::nonNull)
                .map(CctvResponseMapper::from)
                .toList();


        return new GroupTotalResponse(
                groupEntity.getId(),
                groupEntity.getGroupName(),
                groupEntity.getCreatedAt(),
                deviceInfoResponseList,
                cctvInfoResponseList);
    }
}
