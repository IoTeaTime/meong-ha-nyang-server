package org.ioteatime.meonghanyangserver.common.constant;

import lombok.Getter;

@Getter
public enum JwtTokenType {
    MEMBER_JWT("memberJwt"),
    CCTV_JWT("cctvJwt");

    private String value;

    JwtTokenType(String value) {
        this.value = value;
    }
}
