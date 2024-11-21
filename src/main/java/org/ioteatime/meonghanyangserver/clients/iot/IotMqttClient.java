package org.ioteatime.meonghanyangserver.clients.iot;

import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.crt.mqtt.MqttClientConnection;
import software.amazon.awssdk.crt.mqtt.MqttMessage;
import software.amazon.awssdk.crt.mqtt.QualityOfService;

@Service
@RequiredArgsConstructor
public class IotMqttClient {
    @Value("${aws.iot-end-point}")
    private String endpoint;

    private final MqttClientConnection mqttClientConnection;

    public void subscribe(String topic, Consumer<MqttMessage> handler) {
        mqttClientConnection.subscribe(topic, QualityOfService.AT_LEAST_ONCE, handler);
    }

    public void publish(MqttMessage message) {
        mqttClientConnection.publish(message);
    }
}
