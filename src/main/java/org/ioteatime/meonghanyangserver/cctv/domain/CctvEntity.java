package org.ioteatime.meonghanyangserver.cctv.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ioteatime.meonghanyangserver.device.doamin.DeviceEntity;

@Data
@Entity
@NoArgsConstructor
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

    @Builder
    public CctvEntity(Long id, String cctvNickname, String kvsChannelName, DeviceEntity device) {
        this.id = id;
        this.cctvNickname = cctvNickname;
        this.kvsChannelName = kvsChannelName;
        this.device = device;
    }
}
