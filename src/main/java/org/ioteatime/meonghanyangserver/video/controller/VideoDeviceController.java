package org.ioteatime.meonghanyangserver.video.controller;

import lombok.RequiredArgsConstructor;
import org.ioteatime.meonghanyangserver.common.api.Api;
import org.ioteatime.meonghanyangserver.common.type.VideoSuccessType;
import org.ioteatime.meonghanyangserver.common.utils.LoginMember;
import org.ioteatime.meonghanyangserver.video.dto.request.VideoNameRequest;
import org.ioteatime.meonghanyangserver.video.dto.response.VideoPresignedUrlResponse;
import org.ioteatime.meonghanyangserver.video.service.VideoService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/video-device")
public class VideoDeviceController implements VideoDeviceApi {
    private final VideoService videoService;

    @PostMapping("/presigned-url")
    public Api<VideoPresignedUrlResponse> getVideoPresignedUrlForUpload(
            @LoginMember Long cctvId, @RequestBody VideoNameRequest videoNameRequest) {
        VideoPresignedUrlResponse videoPresignedUrlResponse =
                videoService.generateVideoPresignedUrl(cctvId, videoNameRequest.videoName());
        return Api.success(VideoSuccessType.GENERATE_PRESIGNED_URL, videoPresignedUrlResponse);
    }
}
