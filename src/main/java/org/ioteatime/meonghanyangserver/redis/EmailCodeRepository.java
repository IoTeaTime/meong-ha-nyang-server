package org.ioteatime.meonghanyangserver.redis;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface EmailCodeRepository extends CrudRepository<EmailCode, String> {
    Optional<EmailCode> findByEmail(String email);
}
