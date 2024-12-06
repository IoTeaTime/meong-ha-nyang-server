package org.ioteatime.meonghanyangserver.cctv.mapper;

import java.util.List;
import org.ioteatime.meonghanyangserver.cctv.domain.CctvEntity;
import org.ioteatime.meonghanyangserver.cctv.dto.db.CctvWithGroupId;
import org.ioteatime.meonghanyangserver.cctv.dto.response.CctvInfoListResponse;
import org.ioteatime.meonghanyangserver.cctv.dto.response.CctvInfoResponse;
import org.ioteatime.meonghanyangserver.cctv.dto.response.CctvNodeInfoResponse;
import org.ioteatime.meonghanyangserver.cctv.dto.response.CreateCctvResponse;

public class CctvResponseMapper {
    public static CctvInfoResponse from(CctvEntity cctvEntity) {
        return new CctvInfoResponse(
                cctvEntity.getId(),
                cctvEntity.getCctvNickname(),
                cctvEntity.getThingId(),
                cctvEntity.getKvsChannelName());
    }

    public static CctvNodeInfoResponse CctvSelfInfoFrom(CctvWithGroupId cctvWithGroupId) {
        return new CctvNodeInfoResponse(
                cctvWithGroupId.groupId(),
                cctvWithGroupId.cctvId(),
                cctvWithGroupId.cctvNickname(),
                cctvWithGroupId.thingId(),
                cctvWithGroupId.kvsChannelName());
    }

    public static CctvInfoListResponse from(List<CctvInfoResponse> cctvInfoResponseList) {
        return new CctvInfoListResponse(cctvInfoResponseList);
    }

    public static CreateCctvResponse from(Long cctvId, String accessToken) {
        return new CreateCctvResponse(cctvId, accessToken);
    }
}
