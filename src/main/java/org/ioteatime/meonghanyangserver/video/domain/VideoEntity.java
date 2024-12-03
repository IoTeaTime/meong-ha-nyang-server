package org.ioteatime.meonghanyangserver.video.domain;

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

    @JoinColumn(nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private GroupEntity group;

    @Column(nullable = false, length = 100)
    private String videoPath;

    @Column(nullable = false, length = 100)
    private String thumbnailPath;

    @Column(nullable = false)
    private LocalDateTime createdAt;
}
