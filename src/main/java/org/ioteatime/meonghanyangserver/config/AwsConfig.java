package org.ioteatime.meonghanyangserver.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.kinesisvideo.AmazonKinesisVideo;
import com.amazonaws.services.kinesisvideo.AmazonKinesisVideoClient;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsConfig {
    @Value("${aws.kvs.access-key}")
    private String kvsAccessKey;

    @Value("${aws.kvs.secret-key}")
    private String kvsSecretKey;

    @Value("${aws.ses.access-key}")
    private String sesAccessKey;

    @Value("${aws.ses.secret-key}")
    private String sesSecretKey;

    @Value("${aws.s3.access-key}")
    private String s3AccessKey;

    @Value("${aws.s3.secret-key}")
    private String s3SecretKey;

    @Bean
    public AmazonS3 amazonS3() {
        AWSCredentials awsCredentials =
                new AWSCredentials() {
                    @Override
                    public String getAWSAccessKeyId() {
                        return s3AccessKey;
                    }

                    @Override
                    public String getAWSSecretKey() {
                        return s3SecretKey;
                    }
                };
        return AmazonS3Client.builder()
                .withRegion(Regions.AP_NORTHEAST_2)
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
    }

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
                .withRegion(Regions.AP_NORTHEAST_2)
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
    }

    @Bean
    public AmazonSimpleEmailService amazonSimpleEmailService() {
        AWSCredentials awsCredentials =
                new AWSCredentials() {
                    @Override
                    public String getAWSAccessKeyId() {
                        return sesAccessKey;
                    }

                    @Override
                    public String getAWSSecretKey() {
                        return sesSecretKey;
                    }
                };

        return AmazonSimpleEmailServiceClient.builder()
                .withRegion(Regions.AP_NORTHEAST_2)
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
    }
}
