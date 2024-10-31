package org.ioteatime.meonghanyangserver.common.utils;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.ioteatime.meonghanyangserver.cctv.repository.CctvRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KvsChannelNameGenerator {

    private final CctvRepository cctvRepository;

    public String generateUniqueKvsChannelName() {
        while (true) {
            String kvsChannelName = "kvs-" + UUID.randomUUID();
            if (!cctvRepository.existsByKvsChannelName(kvsChannelName)) {
                return kvsChannelName;
            }
        }
    }
}
