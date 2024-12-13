package org.ioteatime.meonghanyangserver.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ioteatime.meonghanyangserver.common.api.Api;
import org.ioteatime.meonghanyangserver.common.exception.ApiExceptionImpl;
import org.ioteatime.meonghanyangserver.common.slack.SlackService;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestController
@Order(value = Integer.MIN_VALUE)
@RestControllerAdvice
@RequiredArgsConstructor
public class ApiExceptionHandler {
    private final SlackService slackService;

    @ExceptionHandler(value = ApiExceptionImpl.class)
    public ResponseEntity<Api<?>> apiResponseEntity(ApiExceptionImpl apiExceptionImpl) {
        log.info("{}", apiExceptionImpl);
        slackService.sendSlackMessage(apiExceptionImpl.getHttpStatus(), apiExceptionImpl, "error");
        return ResponseEntity.status(apiExceptionImpl.getHttpStatus())
                .body(Api.fail(apiExceptionImpl));
    }
}
