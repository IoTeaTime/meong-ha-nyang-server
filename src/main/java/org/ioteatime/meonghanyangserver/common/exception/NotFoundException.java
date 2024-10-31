package org.ioteatime.meonghanyangserver.common.exception;

import org.ioteatime.meonghanyangserver.common.type.ErrorTypeCode;
import org.springframework.http.HttpStatus;

public class NotFoundException extends ApiExceptionImpl {
    public NotFoundException(ErrorTypeCode errorTypeCode) {
        super(errorTypeCode, HttpStatus.NOT_FOUND);
    }
}
