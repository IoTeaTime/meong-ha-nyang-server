package org.ioteatime.meonghanyangserver.common.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CommonErrorType implements ErrorTypeCode {
    // 잘못된 요청
    BAD_REQUEST("BAD REQUEST", "잘못된 요청"),
    // 서버 오류
    SERVER_ERROR("SERVER ERROR", "서버 오류"),
    // null
    NULL_POINT("NULL POINT", "null point 오류"),
    // 인증 실패
    UNAUTHORIZED("UNAUTHORIZED", "인증 실패"),
    INVALID_TYPE("INVALID TYPE", "타입 검증 실패"),
    INVALID_BODY("INVALID BODY", "요청 형식이 올바르지 않습니다.");

    private final String message;
    private final String description;
}
