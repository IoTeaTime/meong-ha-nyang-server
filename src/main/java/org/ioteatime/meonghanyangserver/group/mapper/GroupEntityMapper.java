package org.ioteatime.meonghanyangserver.group.mapper;

import java.time.LocalDateTime;
import org.ioteatime.meonghanyangserver.group.domain.GroupEntity;

public class GroupEntityMapper {
    public static GroupEntity toEntity(String groupName, String fcmTopic) {
        return GroupEntity.builder()
                .groupName(groupName)
                .fcmTopic(fcmTopic)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
