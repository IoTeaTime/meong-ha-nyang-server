package org.ioteatime.meonghanyangserver.image.service;

import com.amazonaws.HttpMethod;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.ioteatime.meonghanyangserver.cctv.repository.CctvRepository;
import org.ioteatime.meonghanyangserver.clients.s3.S3Client;
import org.ioteatime.meonghanyangserver.common.exception.BadRequestException;
import org.ioteatime.meonghanyangserver.common.exception.NotFoundException;
import org.ioteatime.meonghanyangserver.common.type.CctvErrorType;
import org.ioteatime.meonghanyangserver.common.type.GroupErrorType;
import org.ioteatime.meonghanyangserver.common.type.ImageErrorType;
import org.ioteatime.meonghanyangserver.group.domain.GroupEntity;
import org.ioteatime.meonghanyangserver.groupmember.repository.GroupMemberRepository;
import org.ioteatime.meonghanyangserver.image.dto.response.GroupDateImageResponse;
import org.ioteatime.meonghanyangserver.image.dto.response.ImageResponse;
import org.ioteatime.meonghanyangserver.image.dto.response.ImageSaveUrlResponse;
import org.ioteatime.meonghanyangserver.image.mapper.ImageResponseMapper;
import org.ioteatime.meonghanyangserver.image.repository.ImageRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final S3Client s3Client;
    private final CctvRepository cctvRepository;
    private final ImageRepository imageRepository;
    private final GroupMemberRepository groupMemberRepository;

    public ImageSaveUrlResponse getImageSaveUrl(Long cctvId, String fileName) {
        if (!cctvRepository.existsById(cctvId)) {
            throw new NotFoundException(CctvErrorType.NOT_FOUND);
        }
        if (!fileName.matches(".*\\.(jpg|jpeg|png|gif|bmp|tiff)$")) {
            throw new BadRequestException(ImageErrorType.NOT_IMAGE_FILE);
        }

        fileName =
                "img/"
                        + "Mhn_capture_"
                        + cctvId
                        + System.currentTimeMillis()
                        + fileName.substring(fileName.lastIndexOf("."));

        String presignedUrl = s3Client.generatePreSignUrl(fileName, HttpMethod.PUT);
        return ImageResponseMapper.form(presignedUrl);
    }

    public GroupDateImageResponse findAllByMemberIdAndDate(
            Long memberId, Short year, Byte month, Byte day) {
        // 그룹멤버를 찾아 그룹 id 확인
        // 없으면 에러, 이미지를 조회할 수 없음
        // 그룹 id와 날짜를 기준으로 이미지 리스트 조회
        GroupEntity groupEntity =
                groupMemberRepository
                        .findGroupFromGroupMember(memberId)
                        .orElseThrow(
                                () -> new NotFoundException(GroupErrorType.GROUP_MEMBER_NOT_FOUND));
        LocalDateTime searchDate =
                LocalDateTime.of(year.intValue(), month.intValue(), day.intValue(), 0, 0);
        List<ImageResponse> imageResponses =
                imageRepository.findAllByGroupIdAndDate(groupEntity.getId(), searchDate).stream()
                        .map(
                                image -> {
                                    // 경로를 PresignedUrl로 변경
                                    String preSignedUrl =
                                            s3Client.generatePreSignUrl(
                                                    image.getImagePath(), HttpMethod.GET);
                                    return image.updateToPresignedUrl(preSignedUrl);
                                })
                        .toList();
        return ImageResponseMapper.from(imageResponses);
    }
}
