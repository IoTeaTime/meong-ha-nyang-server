package org.ioteatime.meonghanyangserver.handler;

import lombok.extern.slf4j.Slf4j;
import org.ioteatime.meonghanyangserver.common.api.Api;
import org.ioteatime.meonghanyangserver.common.error.TypeCodeIfs;
import org.ioteatime.meonghanyangserver.common.exception.ApiException;
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
    @ExceptionHandler(value = ApiException.class)
    public ResponseEntity<Api<Object>> apiResponseEntity(ApiException apiException) {
        log.debug("", apiException);
        TypeCodeIfs typeCodeIfs = apiException.getTypeCodeIfs();
        return ResponseEntity.status(typeCodeIfs.getCode()).body(Api.ERROR(typeCodeIfs));
    }
}
