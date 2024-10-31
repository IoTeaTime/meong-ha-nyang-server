package org.ioteatime.meonghanyangserver.cctv.service;

import lombok.RequiredArgsConstructor;
import org.ioteatime.meonghanyangserver.cctv.repository.CctvRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CctvService {
    private final CctvRepository cctvRepository;
}
