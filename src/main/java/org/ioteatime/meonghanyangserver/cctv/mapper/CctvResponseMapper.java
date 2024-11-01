package org.ioteatime.meonghanyangserver.cctv.mapper;

import org.ioteatime.meonghanyangserver.cctv.domain.CctvEntity;
import org.ioteatime.meonghanyangserver.cctv.dto.response.CctvInfoResponse;

public class CctvResponseMapper {
    public static CctvInfoResponse from(CctvEntity cctvEntity) {
        return new CctvInfoResponse(
                cctvEntity.getId(), cctvEntity.getCctvNickname(), cctvEntity.getKvsChannelName());
    }
}
