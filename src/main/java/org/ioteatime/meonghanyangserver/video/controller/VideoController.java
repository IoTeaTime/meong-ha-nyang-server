package org.ioteatime.meonghanyangserver.video.controller;

import lombok.RequiredArgsConstructor;
import org.ioteatime.meonghanyangserver.common.api.Api;
import org.ioteatime.meonghanyangserver.common.type.VideoSuccessType;
import org.ioteatime.meonghanyangserver.common.utils.LoginMember;
import org.ioteatime.meonghanyangserver.video.dto.response.VideoPresignedUrlResponse;
import org.ioteatime.meonghanyangserver.video.service.VideoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/video")
public class VideoController implements VideoApi {
    private final VideoService videoService;

    @GetMapping("/{videoId}/group/{groupId}")
    public Api<VideoPresignedUrlResponse> getVideoPresignedUrl(
            @LoginMember Long memberId, @PathVariable Long videoId, @PathVariable Long groupId) {
        VideoPresignedUrlResponse videoPresignedUrlResponse =
                videoService.getVideoPresignedUrl(memberId, videoId, groupId);
        return Api.success(VideoSuccessType.GET_PRESIGNED_URL, videoPresignedUrlResponse);
    }
}