package org.ioteatime.meonghanyangserver.member.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.ioteatime.meonghanyangserver.common.exception.BadRequestException;
import org.ioteatime.meonghanyangserver.common.type.AuthErrorType;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "member")
@EntityListeners(AuditingEntityListener.class)
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column private String profileImgUrl;

    @Column private String fcmToken;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Builder
    public MemberEntity(
            Long id,
            String email,
            String password,
            String nickname,
            String profileImgUrl,
            String fcmToken,
            LocalDateTime updatedAt) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.profileImgUrl = profileImgUrl;
        this.fcmToken = fcmToken;
        this.updatedAt = updatedAt;
    }

    public void setPassword(String encodedPassword) {
        if (encodedPassword == null || encodedPassword.isBlank()) {
            throw new BadRequestException(AuthErrorType.PASSWORD_INVALID);
        }
        this.password = encodedPassword;
    }

    public MemberEntity updateFcmToken(String token) {
        this.fcmToken = token;
        return this;
    }
}
