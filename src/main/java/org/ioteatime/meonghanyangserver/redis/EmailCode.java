package org.ioteatime.meonghanyangserver.redis;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@Builder
@AllArgsConstructor
@RedisHash(value = "emailCode", timeToLive = 60 * 3)
public class EmailCode {
    @Id private Long id;

    @Indexed private String email;

    private String code;
}
