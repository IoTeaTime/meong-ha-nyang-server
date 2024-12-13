package org.ioteatime.meonghanyangserver.handler;

import jakarta.validation.UnexpectedTypeException;
import java.io.IOException;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ioteatime.meonghanyangserver.common.api.Api;
import org.ioteatime.meonghanyangserver.common.slack.SlackService;
import org.ioteatime.meonghanyangserver.common.type.CommonErrorType;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestController
@Order(value = Integer.MAX_VALUE)
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final SlackService slackService;

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Api<?>> exception(final DataIntegrityViolationException exception) {
        log.error("{}", exception);
        slackService.sendSlackMessage(HttpStatus.BAD_REQUEST, exception, "error");
        return new ResponseEntity<>(Api.fail(CommonErrorType.INVALID_BODY), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnexpectedTypeException.class)
    public ResponseEntity<Api<?>> exception(final UnexpectedTypeException exception) {
        log.error("{}", exception);
        slackService.sendSlackMessage(HttpStatus.BAD_REQUEST, exception, "error");
        return new ResponseEntity<>(Api.fail(CommonErrorType.INVALID_TYPE), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Api<?>> exception(final NullPointerException exception) {
        log.error("{}", exception);
        slackService.sendSlackMessage(HttpStatus.INTERNAL_SERVER_ERROR, exception, "error");
        return new ResponseEntity<>(
                Api.fail(CommonErrorType.NULL_POINT), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Api<?>> exception(final NoSuchElementException exception) {
        log.error("{}", exception);
        slackService.sendSlackMessage(HttpStatus.INTERNAL_SERVER_ERROR, exception, "error");
        return new ResponseEntity<>(
                Api.fail(CommonErrorType.NO_SUCH_ELEMENT), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<Api<?>> exception(final IOException exception) {
        log.error("{}", exception);
        slackService.sendSlackMessage(HttpStatus.INTERNAL_SERVER_ERROR, exception, "error");
        return new ResponseEntity<>(Api.fail(CommonErrorType.IO), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Api<?>> exception(final IllegalArgumentException exception) {
        log.error("{}", exception);
        slackService.sendSlackMessage(HttpStatus.INTERNAL_SERVER_ERROR, exception, "error");
        return new ResponseEntity<>(
                Api.fail(CommonErrorType.ILLEGAL_ARGUMENT), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Api<?>> handleMethodArgumentNotValidException(
            final MethodArgumentNotValidException exception) {
        slackService.sendSlackMessage(HttpStatus.INTERNAL_SERVER_ERROR, exception, "error");
        return new ResponseEntity<>(
                Api.fail(CommonErrorType.REQUEST_VALIDATION), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Api<?>> exception(final Exception exception) {
        log.error("{}", exception);
        slackService.sendSlackMessage(HttpStatus.INTERNAL_SERVER_ERROR, exception, "error");
        return new ResponseEntity<>(
                Api.fail(CommonErrorType.INTERNAL_SERVER), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
