package org.ioteatime.meonghanyangserver.clients.google;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.*;
import jakarta.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import org.ioteatime.meonghanyangserver.common.exception.InternalServerException;
import org.ioteatime.meonghanyangserver.common.type.FcmErrorType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FcmClient {
    @Value("${google.application-credentials}")
    private String applicationCredentials;

    @PostConstruct
    public void init() {
        try {
            InputStream refreshToken = new FileInputStream(applicationCredentials);

            FirebaseOptions options =
                    FirebaseOptions.builder()
                            .setCredentials(GoogleCredentials.fromStream(refreshToken))
                            .build();

            FirebaseApp.initializeApp(options);
        } catch (IOException e) {
            throw new InternalServerException(FcmErrorType.IO);
        }
    }

    public void subTopic(String token, String topic) {
        try {
            TopicManagementResponse response =
                    FirebaseMessaging.getInstance()
                            .subscribeToTopic(Collections.singletonList(token), topic);
            System.out.println(response.getSuccessCount() + " tokens were subscribed successfully");
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
            throw new InternalServerException(FcmErrorType.SEND_FAILED);
        }
    }

    public void sendPush(String title, String content, String topic) {
        Message message =
                Message.builder()
                        .setNotification(
                                Notification.builder().setTitle(title).setBody(content).build())
                        .setTopic(topic)
                        .build();
        try {
            String response = FirebaseMessaging.getInstance().send(message);
            System.out.println(response);
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
            throw new InternalServerException(FcmErrorType.SEND_FAILED);
        }
    }
}
