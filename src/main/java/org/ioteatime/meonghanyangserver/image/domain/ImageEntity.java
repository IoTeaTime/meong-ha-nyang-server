package org.ioteatime.meonghanyangserver.image.domain;


import jakarta.persistence.*;
import lombok.Getter;
import org.ioteatime.meonghanyangserver.group.domain.GroupEntity;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "image")
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

    @Column(nullable = false)
    private LocalDateTime createdAt;
}
