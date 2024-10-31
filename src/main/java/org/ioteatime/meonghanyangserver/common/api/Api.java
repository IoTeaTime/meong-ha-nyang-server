package org.ioteatime.meonghanyangserver.common.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ioteatime.meonghanyangserver.common.error.TypeCode;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
// 공통 응답 class
public class Api<T> {
    private Result result;

    @Valid private T body;

    // 정상 응답 body 있는 경우
    public static <T> Api<T> OK(T data) {
        Api<T> api = new Api<T>();
        api.result = Result.OK();
        api.body = data;
        return api;
    }

    // 정상 응답 body 없는 경우
    public static Api<Object> OK() {
        Api<Object> api = new Api<>();
        api.result = Result.OK();
        return api;
    }

    // 정상 응답
    public static <T> Api<T> OK(TypeCode typeCode, T data) {
        Api<T> api = new Api<T>();
        api.result = Result.OK(typeCode);
        api.body = data;
        return api;
    }

    // 정상 응답
    public static <T> Api<T> OK(TypeCode typeCode) {
        Api<T> api = new Api<T>();
        api.result = Result.OK(typeCode);
        return api;
    }

    public static Api<Object> CREATE() {
        Api<Object> api = new Api<>();
        api.result = Result.CREATE();
        return api;
    }

    public static <T> Api<T> CREATE(T data) {
        Api<T> api = new Api<T>();
        api.result = Result.CREATE();
        api.body = data;
        return api;
    }

    public static Api<Object> CREATE(TypeCode typeCode) {
        Api<Object> api = new Api<>();
        api.result = Result.CREATE(typeCode);
        return api;
    }

    public static <T> Api<T> CREATE(TypeCode typeCode, T data) {
        Api<T> api = new Api<T>();
        api.result = Result.CREATE(typeCode);
        api.body = data;
        return api;
    }

    // 에러 응답
    public static Api<Object> ERROR(TypeCode typeCode) {
        Api<Object> api = new Api<Object>();
        api.result = Result.ERROR(typeCode);
        return api;
    }
}
