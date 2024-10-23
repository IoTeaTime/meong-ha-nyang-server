package org.ioteatime.meonghanyangserver.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode implements ErrorCodeIfs{
    //성공
    OK(200,200,"OK"),
    //생성
    CREATE(201,201,"CREATE"),
    //잘못된 요청
    BAD_REQUEST(HttpStatus.BAD_REQUEST.value(), 400,"bad request"),
    //서버 오류
    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), 500,"server error"),
    //null
    NULL_POINT(HttpStatus.INTERNAL_SERVER_ERROR.value(), 512,"Null point"),
    //인증 실패
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED.value(),401,"UNAUTHORIZED")
    ;

    private final Integer httpStatusCode;
    private final Integer errorCode;
    private final String description;
}