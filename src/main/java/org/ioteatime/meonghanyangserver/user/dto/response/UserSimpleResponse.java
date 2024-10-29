package org.ioteatime.meonghanyangserver.user.dto.response;

import org.ioteatime.meonghanyangserver.user.domain.UserEntity;

public record UserSimpleResponse(Long id, String email) {
    public static UserSimpleResponse from(UserEntity user) {
        return new UserSimpleResponse(user.getId(), user.getEmail());
    }
}
