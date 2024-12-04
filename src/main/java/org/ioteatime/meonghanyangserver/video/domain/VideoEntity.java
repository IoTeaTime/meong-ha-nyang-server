package org.ioteatime.meonghanyangserver.video.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Data;
import org.ioteatime.meonghanyangserver.group.domain.GroupEntity;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@Entity
@Table(name = "video")
@EntityListeners(AuditingEntityListener.class)
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

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdAt;
}
