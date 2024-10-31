package org.ioteatime.meonghanyangserver.common.exception;

import org.ioteatime.meonghanyangserver.common.type.ErrorTypeCode;
import org.springframework.http.HttpStatus;

public class BadRequestException extends ApiExceptionImpl {
    public BadRequestException(ErrorTypeCode errorTypeCode) {
        super(errorTypeCode, HttpStatus.BAD_REQUEST);
    }
}
