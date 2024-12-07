package org.ioteatime.meonghanyangserver.clients.iot;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.ioteatime.meonghanyangserver.cctv.domain.CctvEntity;
import org.ioteatime.meonghanyangserver.cctv.repository.CctvRepository;
import org.ioteatime.meonghanyangserver.clients.google.FcmClient;
import org.ioteatime.meonghanyangserver.clients.iot.dto.AIDetectPayload;
import org.ioteatime.meonghanyangserver.clients.iot.dto.AIObject;
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

            AIObject aiObject = getObjectTypeFromPayload(mqttMessage.getPayload());
            String message =
                    writeMessage(cctv.getGroup().getGroupName(), cctv.getCctvNickname(), aiObject);

            fcmClient.sendPush("뽀삐가 나타났어요!", message, cctv.getGroup().getFcmTopic());
            log.info("[객체 탐지 알림] {}", "다음과 같은 메시지를 전송했습니다. : " + message);
            log.info(
                    "[객체 탐지 알림] {}",
                    cctv.getGroup().getGroupName() + " 그룹에 객체 탐지 푸시 알림 전송을 성공하였습니다.");
        }
    }

    private static String getThingIdFromTopic(MqttMessage mqttMessage) {
        String topic = mqttMessage.getTopic();
        return topic.substring(topic.lastIndexOf('/') + 1);
    }

    private AIObject getObjectTypeFromPayload(byte[] payload) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            AIDetectPayload detectPayload = objectMapper.readValue(payload, AIDetectPayload.class);
            log.info("[객체 탐지 Payload] {}", detectPayload.objectType());
            return detectPayload.objectType();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return AIObject.other;
    }

    private String writeMessage(String groupName, String cctvName, AIObject aiObject) {
        return groupName
                + "의 "
                + cctvName
                + "에서 "
                + aiObject.getValue()
                + getParticle(aiObject)
                + " 나타났어요!";
    }

    private String getParticle(AIObject aiObject) {
        String particle;
        switch (aiObject) {
            case person -> particle = "이";
            default -> particle = "가";
        }
        return particle;
    }
}
