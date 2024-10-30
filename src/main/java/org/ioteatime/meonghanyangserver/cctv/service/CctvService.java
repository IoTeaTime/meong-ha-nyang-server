package org.ioteatime.meonghanyangserver.cctv.service;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.ioteatime.meonghanyangserver.cctv.repository.CctvRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CctvService {

    private final CctvRepository cctvRepository;

    public String generateKvsChannelName() {
        while (true) {
            String kvsChannelName = "kvs-" + UUID.randomUUID();
            if (!cctvRepository.existsByKvsChannelName(kvsChannelName)) {
                return kvsChannelName;
            }
        }
    }
}
