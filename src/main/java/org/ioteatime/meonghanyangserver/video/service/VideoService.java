package org.ioteatime.meonghanyangserver.video.service;

import com.amazonaws.HttpMethod;
import lombok.RequiredArgsConstructor;
import org.ioteatime.meonghanyangserver.clients.s3.S3Client;
import org.ioteatime.meonghanyangserver.common.exception.NotFoundException;
import org.ioteatime.meonghanyangserver.common.type.GroupErrorType;
import org.ioteatime.meonghanyangserver.common.type.VideoErrorType;
import org.ioteatime.meonghanyangserver.groupmember.repository.GroupMemberRepository;
import org.ioteatime.meonghanyangserver.video.domain.VideoEntity;
import org.ioteatime.meonghanyangserver.video.dto.response.VideoPresignedUrlResponse;
import org.ioteatime.meonghanyangserver.video.mapper.VideoResponseMapper;
import org.ioteatime.meonghanyangserver.video.repository.VideoRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VideoService {
    private final VideoRepository videoRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final S3Client s3Client;

    public VideoPresignedUrlResponse getVideoPresignedUrl(
            Long memberId, Long videoId, Long groupId) {
        if (!groupMemberRepository.existsByMemberIdAndGroupId(memberId, groupId)) {
            throw new NotFoundException(GroupErrorType.GROUP_MEMBER_NOT_FOUND);
        }
        VideoEntity videoEntity =
                videoRepository
                        .findById(videoId)
                        .orElseThrow(() -> new NotFoundException(VideoErrorType.VIDEO_NOT_FOUND));
        String presignedURL =
                s3Client.generatePreSignUrl(videoEntity.getVideoName(), HttpMethod.GET);
        return VideoResponseMapper.form(presignedURL);
    }
}
