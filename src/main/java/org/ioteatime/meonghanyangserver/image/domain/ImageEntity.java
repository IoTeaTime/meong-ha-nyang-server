package org.ioteatime.meonghanyangserver.image.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Getter;
import org.ioteatime.meonghanyangserver.group.domain.GroupEntity;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Entity
@Table(name = "image")
@EntityListeners(AuditingEntityListener.class)
public class ImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String imageName;

    @JoinColumn(nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private GroupEntity group;

    @Column(nullable = false, length = 100)
    private String imagePath;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdAt;
}
