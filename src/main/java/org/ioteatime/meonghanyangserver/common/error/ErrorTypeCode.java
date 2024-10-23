package org.ioteatime.meonghanyangserver.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorTypeCode implements TypeCodeIfs {
    // 잘못된 요청
    BAD_REQUEST(400, "bad request", "잘못된 요청"),
    // 서버 오류
    SERVER_ERROR(500, "server error", "서버 오류"),
    // null
    NULL_POINT(512, "Null point", "null point 오류"),
    // 인증 실패
    UNAUTHORIZED(401, "UNAUTHORIZED", "인증 실패");

    private final Integer code;
    private final String message;
    private final String description;
}
