package org.ioteatime.meonghanyangserver.cctv.mapper;

import java.util.List;
import org.ioteatime.meonghanyangserver.cctv.domain.CctvEntity;
import org.ioteatime.meonghanyangserver.cctv.dto.response.CctvInfoListResponse;
import org.ioteatime.meonghanyangserver.cctv.dto.response.CctvInfoResponse;

public class CctvResponseMapper {
    public static CctvInfoResponse from(CctvEntity cctvEntity) {
        return new CctvInfoResponse(
                cctvEntity.getId(),
                cctvEntity.getCctvNickname(),
                cctvEntity.getThingId(),
                cctvEntity.getKvsChannelName());
    }

    public static CctvInfoListResponse from(List<CctvInfoResponse> cctvInfoResponseList) {
        return new CctvInfoListResponse(cctvInfoResponseList);
    }
}
