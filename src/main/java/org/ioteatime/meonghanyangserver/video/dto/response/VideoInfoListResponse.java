package org.ioteatime.meonghanyangserver.video.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;

@Schema(description = "동영상 정보 목록 조회 응답")
public record VideoInfoListResponse(@Schema(description = "비디오 정보") List<VideoInfoResponse> video) {
    @Builder
    public VideoInfoListResponse(List<VideoInfoResponse> video) {
        this.video = video;
    }
}
