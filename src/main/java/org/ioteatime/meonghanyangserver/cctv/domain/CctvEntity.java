package org.ioteatime.meonghanyangserver.cctv.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ioteatime.meonghanyangserver.group.domain.GroupEntity;

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

    @Column(nullable = false, length = 200)
    private String thingId;

    @ManyToOne
    @JoinColumn(nullable = false)
    private GroupEntity group;

    @Builder
    public CctvEntity(
            Long id,
            String cctvNickname,
            String kvsChannelName,
            String thingId,
            GroupEntity group) {
        this.id = id;
        this.cctvNickname = cctvNickname;
        this.kvsChannelName = kvsChannelName;
        this.thingId = thingId;
        this.group = group;
    }

    @Builder
    public CctvEntity(Long id, String cctvNickname, String kvsChannelName, String thingId) {
        this.id = id;
        this.cctvNickname = cctvNickname;
        this.kvsChannelName = kvsChannelName;
        this.thingId = thingId;
    }
}
