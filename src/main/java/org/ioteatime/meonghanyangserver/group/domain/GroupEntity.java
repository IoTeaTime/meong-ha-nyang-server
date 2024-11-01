package org.ioteatime.meonghanyangserver.group.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ioteatime.meonghanyangserver.cctv.domain.CctvEntity;
import org.ioteatime.meonghanyangserver.device.doamin.DeviceEntity;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "`group`")
public class GroupEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String groupName;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "group")
    private List<DeviceEntity> deviceEntities;
}
