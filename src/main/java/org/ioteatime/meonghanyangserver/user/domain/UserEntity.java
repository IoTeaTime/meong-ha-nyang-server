package org.ioteatime.meonghanyangserver.user.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.ioteatime.meonghanyangserver.common.error.ErrorTypeCode;
import org.ioteatime.meonghanyangserver.common.exception.ApiException;

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
    public UserEntity(String nickname, String email, String password, String profileImgUrl) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.profileImgUrl = profileImgUrl;
    }

    public void setPassword(String encodedPassword) {
        if (encodedPassword == null || encodedPassword.isBlank()) {
            throw new ApiException(
                    ErrorTypeCode.BAD_REQUEST, "Password is empty");
        }
        this.password = encodedPassword;
    }
}
