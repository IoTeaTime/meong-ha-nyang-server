package org.ioteatime.meonghanyangserver.fcm.mapper;

import org.ioteatime.meonghanyangserver.fcm.dto.response.FcmTopicResponse;

public class FcmMapper {
    public static FcmTopicResponse from(String topic) {
        return new FcmTopicResponse(topic);
    }
}
