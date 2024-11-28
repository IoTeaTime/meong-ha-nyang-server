package org.ioteatime.meonghanyangserver.common.slack;

import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import java.io.IOException;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SlackService {
    @Value("${slack.key}")
    String slackToken;

    public void sendSlackMessage(Throwable message, String channel) {
        if (slackToken.isEmpty()) {
            log.info("DEV MODE - Slack error 메시지를 전송하지 않습니다.");
            return;
        }

        String channelAddress = "";

        // 채널 값을 전달받아 올바른 슬랙채널로 분기
        if (channel.equals("error")) {
            channelAddress = SlackEnum.ERROR_CHANNEL.getValue();
        }

        try {
            MethodsClient methods = Slack.getInstance().methods(slackToken);

            ChatPostMessageRequest request =
                    ChatPostMessageRequest.builder()
                            .channel(channelAddress)
                            .text(message.getMessage())
                            .build();

            ChatPostMessageResponse chatPostMessageResponse = methods.chatPostMessage(request);
            String ts = chatPostMessageResponse.getTs();

            ChatPostMessageRequest replyRequest =
                    ChatPostMessageRequest.builder()
                            .channel(channelAddress)
                            .text(Arrays.toString(message.getStackTrace()))
                            .threadTs(ts)
                            .build();

            methods.chatPostMessage(replyRequest);

            log.info("Slack " + channel + " 에 메시지 전송 성공");
        } catch (SlackApiException | IOException e) {
            log.error(e.getMessage());
        }
    }
}
