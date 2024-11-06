package org.ioteatime.meonghanyangserver.video.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Data;
import org.ioteatime.meonghanyangserver.group.domain.GroupEntity;

@Data
@Entity
@Table(name = "video")
public class VideoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String videoName;

    @ManyToOne
    @JoinColumn(nullable = false)
    private GroupEntity group;

    @Column(nullable = false, length = 100)
    private String videoPath;

    @Column(nullable = false)
    private LocalDateTime createdAt;
}
