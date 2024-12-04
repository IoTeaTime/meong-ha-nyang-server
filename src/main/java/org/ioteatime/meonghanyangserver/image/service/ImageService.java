package org.ioteatime.meonghanyangserver.image.service;

import com.amazonaws.HttpMethod;
import lombok.RequiredArgsConstructor;
import org.ioteatime.meonghanyangserver.cctv.repository.CctvRepository;
import org.ioteatime.meonghanyangserver.clients.s3.S3Client;
import org.ioteatime.meonghanyangserver.common.exception.NotFoundException;
import org.ioteatime.meonghanyangserver.common.type.CctvErrorType;
import org.ioteatime.meonghanyangserver.image.dto.response.ImageSaveUrlResponse;
import org.ioteatime.meonghanyangserver.image.mapper.ImageResponseMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final S3Client s3Client;
    private final CctvRepository cctvRepository;

    public ImageSaveUrlResponse getImageSaveUrl(Long cctvId, String fileName) {
        if (!cctvRepository.existsById(cctvId)) {
            throw new NotFoundException(CctvErrorType.NOT_FOUND);
        }

        String presignedUrl = s3Client.generatePreSignUrl(fileName, HttpMethod.PUT);
        return ImageResponseMapper.form(presignedUrl);
    }
}
