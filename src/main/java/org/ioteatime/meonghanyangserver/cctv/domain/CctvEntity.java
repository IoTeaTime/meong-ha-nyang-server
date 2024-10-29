package org.ioteatime.meonghanyangserver.cctv.domain;

import jakarta.persistence.*;
import lombok.Data;
import org.ioteatime.meonghanyangserver.group.domain.GroupEntity;

@Data
@Entity
@Table(name = "cctv")
public class CctvEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private GroupEntity group;

    @Column(nullable = false, length = 20)
    private String cctvNickname;

    @Column(nullable = false, length = 100)
    private String kvsChannelName;
}
