package org.ioteatime.meonghanyangserver.auth.dto.db;

import org.ioteatime.meonghanyangserver.groupmember.doamin.enums.GroupMemberRole;

public record LoginWithMemberInfo(
        Long memberId,
        String nickname,
        String password,
        boolean isGroupMember,
        GroupMemberRole role) {}
