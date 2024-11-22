package org.ioteatime.meonghanyangserver.handler;

import jakarta.validation.UnexpectedTypeException;
import lombok.extern.slf4j.Slf4j;
import org.ioteatime.meonghanyangserver.common.api.Api;
import org.ioteatime.meonghanyangserver.common.exception.ApiException;
import org.ioteatime.meonghanyangserver.common.exception.ApiExceptionImpl;
import org.ioteatime.meonghanyangserver.common.type.CommonErrorType;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestController
@Order(value = Integer.MAX_VALUE)
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Api<Object>> exception(final DataIntegrityViolationException exception) {
        log.error("{}", exception);
        return new ResponseEntity<>(Api.fail(CommonErrorType.INVALID_BODY), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnexpectedTypeException.class)
    public ResponseEntity<Api<Object>> exception(final UnexpectedTypeException exception) {
        log.error("{}", exception);
        return new ResponseEntity<>(Api.fail(CommonErrorType.INVALID_TYPE), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ApiExceptionImpl.class)
    public ResponseEntity<Api<Object>> exception(ApiException exception) {
        log.info("", exception);
        return ResponseEntity.status(exception.getHttpStatus()).body(Api.fail(exception));
    }
}
