package org.ioteatime.meonghanyangserver.group.domain;

import jakarta.persistence.*;
import lombok.Data;
import org.ioteatime.meonghanyangserver.cctv.domain.CctvEntity;
import org.ioteatime.meonghanyangserver.video.entity.VideoEntity;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "group")
public class GroupEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String groupName;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private List<CctvEntity> cctvs;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private List<VideoEntity> videos;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private List<GroupUserEntity> groupUsers;
}