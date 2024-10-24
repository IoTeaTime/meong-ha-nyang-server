package org.ioteatime.meonghanyangserver.user.repository;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column private String profile_img_url;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;
}
