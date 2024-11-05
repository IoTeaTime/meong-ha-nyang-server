package org.ioteatime.meonghanyangserver.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.services.kinesisvideo.AmazonKinesisVideo;
import com.amazonaws.services.kinesisvideo.AmazonKinesisVideoClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsConfig {
    @Value("${aws.kvs.access-key}")
    private String kvsAccessKey;

    @Value("${aws.kvs.secret-key}")
    private String kvsSecretKey;

    @Bean
    public AmazonKinesisVideo amazonKinesisVideo() {
        AWSCredentials awsCredentials =
                new AWSCredentials() {
                    @Override
                    public String getAWSAccessKeyId() {
                        return kvsAccessKey;
                    }

                    @Override
                    public String getAWSSecretKey() {
                        return kvsSecretKey;
                    }
                };

        return AmazonKinesisVideoClient.builder()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
    }
}
