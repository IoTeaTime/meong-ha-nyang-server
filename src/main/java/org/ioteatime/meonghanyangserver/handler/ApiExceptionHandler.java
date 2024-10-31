package org.ioteatime.meonghanyangserver.handler;

import lombok.extern.slf4j.Slf4j;
import org.ioteatime.meonghanyangserver.common.api.Api;
import org.ioteatime.meonghanyangserver.common.exception.ApiExceptionImpl;
import org.ioteatime.meonghanyangserver.common.type.ErrorTypeCode;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestController
@Order(value = Integer.MIN_VALUE)
@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(value = ApiExceptionImpl.class)
    public ResponseEntity<Api<Object>> apiResponseEntity(ApiExceptionImpl apiExceptionImpl) {
        log.debug("", apiExceptionImpl);
        ErrorTypeCode errorTypeCode = apiExceptionImpl.getErrorTypeCode();
        return ResponseEntity.status(apiExceptionImpl.getHttpStatus())
                .body(Api.fail(errorTypeCode));
    }
}
