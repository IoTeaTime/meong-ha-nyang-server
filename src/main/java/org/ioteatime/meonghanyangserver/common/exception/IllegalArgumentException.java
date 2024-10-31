package org.ioteatime.meonghanyangserver.common.exception;

import org.ioteatime.meonghanyangserver.common.type.ErrorTypeCode;
import org.springframework.http.HttpStatus;

public class IllegalArgumentException extends ApiExceptionImpl {
    public IllegalArgumentException(ErrorTypeCode errorTypeCode) {
        super(errorTypeCode, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
