package org.ioteatime.meonghanyangserver.image.controller;

import lombok.RequiredArgsConstructor;
import org.ioteatime.meonghanyangserver.common.api.Api;
import org.ioteatime.meonghanyangserver.common.type.ImageSuccessType;
import org.ioteatime.meonghanyangserver.common.utils.LoginMember;
import org.ioteatime.meonghanyangserver.image.dto.response.GroupDateImageResponse;
import org.ioteatime.meonghanyangserver.image.service.ImageService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/image")
public class ImageController implements ImageApi {
    private final ImageService imageService;

    @GetMapping
    public Api<GroupDateImageResponse> groupDateImageList(
            @LoginMember Long memberId,
            @RequestParam("year") Short year,
            @RequestParam("month") Byte month,
            @RequestParam("day") Byte day) {
        GroupDateImageResponse response =
                imageService.findAllByMemberIdAndDate(memberId, year, month, day);
        return Api.success(ImageSuccessType.GET_LIST_OF_DATE, response);
    }
}
