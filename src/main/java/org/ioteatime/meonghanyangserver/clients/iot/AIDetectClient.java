package org.ioteatime.meonghanyangserver.clients.iot;

import java.util.Arrays;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.ioteatime.meonghanyangserver.cctv.domain.CctvEntity;
import org.ioteatime.meonghanyangserver.cctv.repository.CctvRepository;
import org.ioteatime.meonghanyangserver.clients.google.FcmClient;
import org.ioteatime.meonghanyangserver.common.exception.BadRequestException;
import org.ioteatime.meonghanyangserver.common.type.IoTErrorType;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.crt.mqtt.MqttMessage;

// 객체 탐지 클라이언트
@Slf4j
@Component
public class AIDetectClient {
    private final FcmClient fcmClient;
    private final IotMqttClient iotMqttClient;
    private final CctvRepository cctvRepository;

    public AIDetectClient(
            FcmClient fcmClient, IotMqttClient iotMqttClient, CctvRepository cctvRepository) {
        this.fcmClient = fcmClient;
        this.iotMqttClient = iotMqttClient;
        this.cctvRepository = cctvRepository;

        iotMqttClient.subscribe("/mhn/event/detect/things/#", this::detectEventHandler);
        iotMqttClient.subscribe("/mhn/event/detect/things/*", this::detectEventHandler);
        log.info("[객체 탐지] {}", "탐지 Topic을 구독하였습니다.");
    }

    private void detectEventHandler(MqttMessage mqttMessage) {
        log.info("[객체 탐지] {}", "탐지 Topic이 발행되었습니다.");
        // thingId 기준 GroupId 검색
        if (mqttMessage.getTopic() == null || mqttMessage.getTopic().isEmpty()) {
            throw new BadRequestException(IoTErrorType.TOPIC_NULL);
        }
        Optional<CctvEntity> cctvEntity =
                cctvRepository.findByThingId(getThingIdFromTopic(mqttMessage));
        if (cctvEntity.isPresent()) {
            CctvEntity cctv = cctvEntity.get();
            fcmClient.sendPush(
                    "뽀삐가 나타났어요!",
                    Arrays.toString(mqttMessage.getPayload()), // Payload 수정 필요
                    cctv.getGroup().getFcmTopic());
            log.info(
                    "[객체 탐지 알림] {}",
                    cctv.getGroup().getGroupName() + " 그룹에 객체 탐지 푸시 알림 전송을 성공하였습니다.");
        }
    }

    private static String getThingIdFromTopic(MqttMessage mqttMessage) {
        String topic = mqttMessage.getTopic();
        return topic.substring(topic.lastIndexOf('/') + 1);
    }
}
