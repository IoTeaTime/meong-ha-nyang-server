package org.ioteatime.meonghanyangserver.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.iot.AWSIot;
import com.amazonaws.services.iot.AWSIotClient;
import com.amazonaws.services.iot.model.*;
import com.amazonaws.services.kinesisvideo.AmazonKinesisVideo;
import com.amazonaws.services.kinesisvideo.AmazonKinesisVideoClient;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ioteatime.meonghanyangserver.common.exception.InternalServerException;
import org.ioteatime.meonghanyangserver.common.type.AwsErrorType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.crt.CRT;
import software.amazon.awssdk.crt.mqtt.MqttClientConnection;
import software.amazon.awssdk.crt.mqtt.MqttClientConnectionEvents;
import software.amazon.awssdk.iot.AwsIotMqttConnectionBuilder;
import software.amazon.awssdk.iot.iotshadow.IotShadowClient;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class AwsConfig {
    private final AwsProperties awsProperties;

    @Bean
    public AmazonS3 amazonS3() {
        AWSCredentials awsCredentials =
                new AWSCredentials() {
                    @Override
                    public String getAWSAccessKeyId() {
                        return awsProperties.s3AccessKey();
                    }

                    @Override
                    public String getAWSSecretKey() {
                        return awsProperties.s3SecretKey();
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
                        return awsProperties.kvsAccessKey();
                    }

                    @Override
                    public String getAWSSecretKey() {
                        return awsProperties.kvsSecretKey();
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
                        return awsProperties.sesAccessKey();
                    }

                    @Override
                    public String getAWSSecretKey() {
                        return awsProperties.sesSecretKey();
                    }
                };

        return AmazonSimpleEmailServiceClient.builder()
                .withRegion(Regions.AP_NORTHEAST_2)
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
    }

    @Bean
    public AWSIot awsIot() {
        AWSCredentials awsCredentials =
                new AWSCredentials() {
                    @Override
                    public String getAWSAccessKeyId() {
                        return awsProperties.iotAccessKey();
                    }

                    @Override
                    public String getAWSSecretKey() {
                        return awsProperties.iotSecretKey();
                    }
                };

        return AWSIotClient.builder()
                .withRegion(Regions.AP_NORTHEAST_2)
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
    }

    @Bean
    public MqttClientConnection awsIotMqttClient(@Autowired AWSIot awsIot) {
        // 키 생성 및 정책 추가
        CreateKeysAndCertificateResult keysAndCertificate =
                getCreateKeysAndCertificateResult(awsIot);

        // MQTT 연결
        MqttClientConnection connection = createConnection(keysAndCertificate);
        connection.connect();

        return connection;
    }

    @Bean
    public IotShadowClient iotShadowClient(@Autowired MqttClientConnection awsIotMqttClient) {
        return new IotShadowClient(awsIotMqttClient);
    }

    private CreateKeysAndCertificateResult getCreateKeysAndCertificateResult(AWSIot awsIot) {
        // 키 생성
        CreateKeysAndCertificateResult keysAndCertificate =
                awsIot.createKeysAndCertificate(
                        new CreateKeysAndCertificateRequest().withSetAsActive(true));

        // 정책 추가
        AttachPolicyRequest attachPolicyRequest = new AttachPolicyRequest();
        attachPolicyRequest.setPolicyName("certified_thing");
        attachPolicyRequest.setTarget(keysAndCertificate.getCertificateArn());
        awsIot.attachPolicy(attachPolicyRequest);

        return keysAndCertificate;
    }

    private MqttClientConnection createConnection(CreateKeysAndCertificateResult result) {
        try {
            AwsIotMqttConnectionBuilder builder =
                    AwsIotMqttConnectionBuilder.newMtlsBuilder(
                            result.getCertificatePem(), result.getKeyPair().getPrivateKey());

            // 연결 핸들러
            MqttClientConnectionEvents callbacks =
                    new MqttClientConnectionEvents() {
                        @Override
                        public void onConnectionInterrupted(int errorCode) {
                            if (errorCode != 0) {
                                log.debug(
                                        "Connection interrupted: "
                                                + errorCode
                                                + ": "
                                                + CRT.awsErrorString(errorCode));
                            }
                        }

                        @Override
                        public void onConnectionResumed(boolean sessionPresent) {
                            log.debug(
                                    "Connection resumed: "
                                            + (sessionPresent
                                                    ? "existing session"
                                                    : "clean session"));
                        }
                    };

            // 연결 설정
            builder.withConnectionEventCallbacks(callbacks)
                    .withClientId(awsProperties.iotClientId())
                    .withEndpoint(awsProperties.iotEndpoint())
                    .withCleanSession(true)
                    .withKeepAliveSecs(300);

            // 연결 후 builder 닫기
            MqttClientConnection connection = builder.build();
            builder.close();

            return connection;
        } catch (Throwable e) {
            e.printStackTrace();
            throw new InternalServerException(AwsErrorType.IOT_MQTT_CONNECTION_FAILED);
        }
    }
}
