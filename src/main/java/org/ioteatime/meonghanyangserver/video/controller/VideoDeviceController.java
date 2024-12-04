package org.ioteatime.meonghanyangserver.video.controller;

import lombok.RequiredArgsConstructor;
import org.ioteatime.meonghanyangserver.common.api.Api;
import org.ioteatime.meonghanyangserver.common.type.VideoSuccessType;
import org.ioteatime.meonghanyangserver.common.utils.LoginMember;
import org.ioteatime.meonghanyangserver.video.dto.response.VideoPresignedUrlResponse;
import org.ioteatime.meonghanyangserver.video.service.VideoService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/video-device")
public class VideoDeviceController implements VideoDeviceApi {
    private final VideoService videoService;

    @GetMapping("/{videoId}/group/{groupId}")
    public Api<VideoPresignedUrlResponse> getVideoPresignedUrl(
            @LoginMember Long memberId, @PathVariable Long videoId, @PathVariable Long groupId) {
        VideoPresignedUrlResponse videoPresignedUrlResponse =
                videoService.getVideoPresignedUrl(memberId, videoId, groupId);
        return Api.success(VideoSuccessType.GET_PRESIGNED_URL, videoPresignedUrlResponse);
    }
}
