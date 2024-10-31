package org.ioteatime.meonghanyangserver.common.exception;

import org.ioteatime.meonghanyangserver.common.type.ErrorTypeCode;
import org.springframework.http.HttpStatus;

public class UnauthorizedException extends ApiExceptionImpl {
    public UnauthorizedException(ErrorTypeCode errorTypeCode) {
        super(errorTypeCode, HttpStatus.UNAUTHORIZED);
    }
}
