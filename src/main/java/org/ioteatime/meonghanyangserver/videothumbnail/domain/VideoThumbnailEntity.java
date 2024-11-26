package org.ioteatime.meonghanyangserver.videothumbnail.domain;

import jakarta.persistence.*;
import lombok.Data;
import org.ioteatime.meonghanyangserver.video.domain.VideoEntity;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "video_thumbnail")
public class VideoThumbnailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(nullable = false)
    private VideoEntity videoEntity;

    @Column(nullable = false, length = 100)
    private String thumbnailPath;

    @Column(nullable = false)
    private LocalDateTime createdAt;
}
