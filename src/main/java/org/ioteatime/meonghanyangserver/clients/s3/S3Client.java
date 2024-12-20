package org.ioteatime.meonghanyangserver.clients.s3;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3Client {
    private final AmazonS3 amazonS3;

    @Value("${aws.s3-bucket}")
    private String bucket;

    public String generatePreSignUrl(
            String filename, HttpMethod httpMethod, Integer standard, Integer amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(standard, amount);
        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(bucket, filename)
                        .withMethod(httpMethod)
                        .withExpiration(calendar.getTime());
        URL url = amazonS3.generatePresignedUrl(generatePresignedUrlRequest);
        return url.toString();
    }

    public void deleteObject(String key) {
        log.info("delete object: {}", key);
        amazonS3.deleteObject(new DeleteObjectRequest(bucket, key));
    }
}
