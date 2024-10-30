package org.ioteatime.meonghanyangserver.group.dto.response;

import java.time.LocalDateTime;

public record CreateGroupResponse(Long groupId, String groupName, LocalDateTime createdAt) {}
