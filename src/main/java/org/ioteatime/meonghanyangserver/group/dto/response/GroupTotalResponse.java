package org.ioteatime.meonghanyangserver.group.dto.response;

import org.ioteatime.meonghanyangserver.cctv.dto.response.CctvInfoResponse;

import java.time.LocalDateTime;
import java.util.List;

public record GroupTotalResponse(Long groupId, String groupName, LocalDateTime createdAt, List<GroupUserInfoResponse> groupUser, List<CctvInfoResponse> cctv) {
}
