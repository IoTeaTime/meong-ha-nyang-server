package org.ioteatime.meonghanyangserver.clients.kvs;

import com.amazonaws.services.kinesisvideo.AmazonKinesisVideo;
import com.amazonaws.services.kinesisvideo.model.DeleteSignalingChannelRequest;
import com.amazonaws.services.kinesisvideo.model.DescribeSignalingChannelRequest;
import com.amazonaws.services.kinesisvideo.model.DescribeSignalingChannelResult;
import com.amazonaws.services.kinesisvideo.model.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.ioteatime.meonghanyangserver.common.exception.NotFoundException;
import org.ioteatime.meonghanyangserver.common.type.AwsErrorType;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KvsClient {
    private final AmazonKinesisVideo amazonKinesisVideo;

    public void deleteSignalingChannel(String channelName) {
        DescribeSignalingChannelRequest signalingChannelRequest =
                new DescribeSignalingChannelRequest();
        signalingChannelRequest.setChannelName(channelName);

        try {
            DescribeSignalingChannelResult signalingChannelResult =
                    amazonKinesisVideo.describeSignalingChannel(signalingChannelRequest);

            DeleteSignalingChannelRequest deleteChannelRequest =
                    new DeleteSignalingChannelRequest();
            deleteChannelRequest.setChannelARN(
                    signalingChannelResult.getChannelInfo().getChannelARN());

            amazonKinesisVideo.deleteSignalingChannel(deleteChannelRequest);
        } catch (ResourceNotFoundException err) {
            throw new NotFoundException(AwsErrorType.KVS_CHANNEL_NAME_NOT_FOUND);
        }
    }
}
