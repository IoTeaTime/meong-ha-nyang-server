package org.ioteatime.meonghanyangserver.video.controller;

import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.ioteatime.meonghanyangserver.common.api.Api;
import org.ioteatime.meonghanyangserver.common.type.VideoSuccessType;
import org.ioteatime.meonghanyangserver.common.utils.LoginMember;
import org.ioteatime.meonghanyangserver.video.dto.response.VideoInfoListResponse;
import org.ioteatime.meonghanyangserver.video.service.VideoService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/video")
public class VideoController implements VideoApi {
    private final VideoService videoService;

    @GetMapping("/group/{groupId}")
    public Api<VideoInfoListResponse> searchToDate(
            @LoginMember Long memberId,
            @PathVariable("groupId") Long groupId,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        VideoInfoListResponse videoInfoListResponse =
                videoService.searchToDate(memberId, groupId, date);
        return Api.success(VideoSuccessType.GET_VIDEO_INFO, videoInfoListResponse);
    }
}
