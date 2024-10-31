package org.ioteatime.meonghanyangserver.common.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.Valid;
import org.ioteatime.meonghanyangserver.common.exception.ApiException;
import org.ioteatime.meonghanyangserver.common.type.ErrorTypeCode;
import org.ioteatime.meonghanyangserver.common.type.SuccessTypeCode;

@JsonPropertyOrder({"result", "body"})
// 공통 응답 class
public record Api<T>(Result result, @JsonInclude(JsonInclude.Include.NON_NULL) @Valid T body) {
    public static <T> Api<T> success(SuccessTypeCode successType) {
        return new Api<>(new Result(successType), null);
    }

    public static <T> Api<T> success(SuccessTypeCode successType, T body) {
        return new Api<>(new Result(successType), body);
    }

    public static <T> Api<T> fail(ApiException exception) {
        ErrorTypeCode errorType = exception.getTypeCode();
        return new Api<>(new Result(exception.getHttpStatus(), errorType), null);
    }

    public static <T> Api<T> fail(ErrorTypeCode errorType) {
        return new Api<>(new Result(errorType), null);
    }

    public static <T> Api<T> fail(ErrorTypeCode errorType, T body) {
        return new Api<>(new Result(errorType), body);
    }
}
