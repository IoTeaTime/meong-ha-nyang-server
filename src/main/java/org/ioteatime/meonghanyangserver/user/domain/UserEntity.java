package org.ioteatime.meonghanyangserver.user.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.ioteatime.meonghanyangserver.common.error.ErrorTypeCode;
import org.ioteatime.meonghanyangserver.common.exception.ApiExceptionImpl;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "user")
public class UserEntity {
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

    @Builder
    public UserEntity(
            Long id, String email, String password, String nickname, String profileImgUrl) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.profileImgUrl = profileImgUrl;
    }

    public void setPassword(String encodedPassword) {
        if (encodedPassword == null || encodedPassword.isBlank()) {
            throw new ApiExceptionImpl(ErrorTypeCode.BAD_REQUEST, "Password is empty");
        }
        this.password = encodedPassword;
    }
}
