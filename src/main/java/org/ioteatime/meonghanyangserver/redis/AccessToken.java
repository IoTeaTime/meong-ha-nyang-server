package org.ioteatime.meonghanyangserver.redis;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@AllArgsConstructor
@Builder
@RedisHash(value = "accessToken")
public class AccessToken {
    @Id private Long id;

    @Indexed private String accessToken;

    @TimeToLive private long ttl;
}
