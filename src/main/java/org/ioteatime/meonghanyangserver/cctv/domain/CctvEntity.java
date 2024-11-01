package org.ioteatime.meonghanyangserver.cctv.domain;

import jakarta.persistence.*;
import lombok.Data;
import org.ioteatime.meonghanyangserver.device.doamin.DeviceEntity;

@Data
@Entity
@Table(name = "cctv")
public class CctvEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String cctvNickname;

    @Column(nullable = false, length = 100)
    private String kvsChannelName;

    @OneToOne
    @JoinColumn(name = "device_id")
    private DeviceEntity device;
}
