package org.ioteatime.meonghanyangserver.common.exception;

import org.ioteatime.meonghanyangserver.common.type.ErrorTypeCode;
import org.springframework.http.HttpStatus;

public class InternalServerException extends ApiExceptionImpl {
    public InternalServerException(ErrorTypeCode errorTypeCode) {
        super(errorTypeCode, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
