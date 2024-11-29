package org.ioteatime.meonghanyangserver.clients.iot;

import java.util.HashMap;
import lombok.RequiredArgsConstructor;
import org.ioteatime.meonghanyangserver.common.exception.InternalServerException;
import org.ioteatime.meonghanyangserver.common.type.IoTErrorType;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.crt.mqtt.QualityOfService;
import software.amazon.awssdk.iot.iotshadow.IotShadowClient;
import software.amazon.awssdk.iot.iotshadow.model.ShadowState;
import software.amazon.awssdk.iot.iotshadow.model.UpdateShadowRequest;

@Service
@RequiredArgsConstructor
public class IotShadowMqttClient {
    private final IotShadowClient iotShadowClient;

    public void updateShadow(String thingName, String key, boolean value) {
        try {
            ShadowState shadowState = new ShadowState();
            shadowState.desired = new HashMap<>();
            shadowState.desired.put(key, value);
            shadowState.reportedIsNullable = true;

            UpdateShadowRequest updateShadowRequest = new UpdateShadowRequest();
            updateShadowRequest.state = shadowState;

            updateShadowRequest.thingName = thingName;

            iotShadowClient.PublishUpdateShadow(
                    updateShadowRequest, QualityOfService.AT_LEAST_ONCE);
        } catch (Exception e) {
            throw new InternalServerException(IoTErrorType.UPDATE_SHADOW);
        }
    }
}
