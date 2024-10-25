package org.ioteatime.meonghanyangserver.user.dto.response;

import lombok.Getter;
import org.ioteatime.meonghanyangserver.user.domain.UserEntity;

@Getter
public record UserDetailResponse(
        Long id,
        String email,
        String profileImgUrl,
        String nickname
) {
    // UserEntity -> UserDetailResponse로 변환하는 정적 메서드
    public static UserDetailResponse from(UserEntity user) {
        return new UserDetailResponse(
                user.getId(),
                user.getEmail(),
                user.getProfileImgUrl(),
                user.getNickname()
        );
    }
}
