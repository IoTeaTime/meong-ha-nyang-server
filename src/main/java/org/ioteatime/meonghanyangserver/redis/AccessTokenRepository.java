package org.ioteatime.meonghanyangserver.redis;

import org.springframework.data.repository.CrudRepository;

public interface AccessTokenRepository extends CrudRepository<AccessToken, String> {
    boolean existsByAccessToken(String accessToken);
}
