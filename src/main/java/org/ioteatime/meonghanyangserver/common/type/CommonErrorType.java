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
    INVALID_BODY("INVALID BODY", "요청 형식이 올바르지 않습니다."),
    NO_SUCH_ELEMENT("NO SUCH ELEMENT", "요소가 존재하지 않습니다"),
    IO("I/O", "I/O 오류가 발생하였습니다."),
    ILLEGAL_ARGUMENT("ILLEGAL ARGUMENT", "인자 값이 올바르지 않습니다."),
    INTERNAL_SERVER("INTERNAL SERVER", "서버 에러가 발생하였습니다. 로그를 확인해 주세요."),
    REQUEST_VALIDATION("INVALID VALUE", "요청 형식이 올바르지 않습니다.");

    private final String message;
    private final String description;
}
