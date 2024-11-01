package org.ioteatime.meonghanyangserver.cctv.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.ioteatime.meonghanyangserver.cctv.dto.db.CctvWithDeviceId;
import org.ioteatime.meonghanyangserver.cctv.repository.CctvRepository;
import org.ioteatime.meonghanyangserver.clients.kvs.KvsClient;
import org.ioteatime.meonghanyangserver.common.exception.BadRequestException;
import org.ioteatime.meonghanyangserver.common.exception.NotFoundException;
import org.ioteatime.meonghanyangserver.common.type.CctvErrorType;
import org.ioteatime.meonghanyangserver.device.repository.DeviceRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CctvService {
    private final KvsClient kvsClient;
    private final CctvRepository cctvRepository;
    private final DeviceRepository deviceRepository;

    @Transactional
    public void deleteById(Long userId, Long cctvId) {
        // Device 테이블에서 userId를 조회하여 role 을 확인
        if (deviceRepository.isMasterUserId(userId)) {
            // MASTER 이면 CCTV 삭제 가능
            CctvWithDeviceId cctv =
                    cctvRepository
                            .findByIdWithDeviceId(cctvId)
                            .orElseThrow(() -> new NotFoundException(CctvErrorType.NOT_FOUND));
            // 1. KVS 시그널링 채널 삭제
            kvsClient.deleteSignalingChannel(cctv.kvsChannelName());
            // 2. CCTV 테이블에서 삭제
            cctvRepository.deleteById(cctvId);
            System.out.println("HWEHREWR");
            // 3. Device 테이블에서 삭제
            deviceRepository.deleteById(cctv.deviceId());
        } else {
            // PARTICIPANT 이면 CCTV 삭제 실패
            throw new BadRequestException(CctvErrorType.ONLY_MASTER_CAN_DELETE);
        }
        // CCTV 인 경우는 현재 MASTER 인 경우와 동일하므로 처리하지 않음
    }
}
