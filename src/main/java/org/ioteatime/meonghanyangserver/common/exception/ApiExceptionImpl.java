package org.ioteatime.meonghanyangserver.common.exception;

import lombok.Getter;
import org.ioteatime.meonghanyangserver.common.type.ErrorTypeCode;
import org.springframework.http.HttpStatus;

@Getter
public class ApiExceptionImpl extends RuntimeException implements ApiException {
    private final ErrorTypeCode errorTypeCode;
    private final HttpStatus httpStatus;

    public ApiExceptionImpl(ErrorTypeCode errorTypeCode, HttpStatus httpStatus) {
        super(errorTypeCode.getDescription());
        this.errorTypeCode = errorTypeCode;
        this.httpStatus = httpStatus;
    }

    @Override
    public ErrorTypeCode getTypeCode() {
        return this.errorTypeCode;
    }
}
