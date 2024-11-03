package org.ioteatime.meonghanyangserver.device.doamin;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ioteatime.meonghanyangserver.cctv.domain.CctvEntity;
import org.ioteatime.meonghanyangserver.device.doamin.enums.DeviceRole;
import org.ioteatime.meonghanyangserver.group.domain.GroupEntity;
import org.ioteatime.meonghanyangserver.member.domain.MemberEntity;

@Data
@Entity
@Table(name = "device")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeviceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 10)
    @Enumerated(value = EnumType.STRING)
    private DeviceRole role;

    @Column(nullable = false, length = 150)
    private String deviceUuid;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private GroupEntity group;

    @ManyToOne
    @JoinColumn(name = "member_id", unique = true)
    private MemberEntity member;

    @OneToOne(mappedBy = "device")
    private CctvEntity cctv;
}
