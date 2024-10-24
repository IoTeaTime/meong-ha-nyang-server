package org.ioteatime.meonghanyangserver.common.api;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ioteatime.meonghanyangserver.common.error.TypeCodeIfs;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
    public static <T> Api<T> OK(TypeCodeIfs typeCodeIfs, T data) {
        Api<T> api = new Api<T>();
        api.result = Result.OK(typeCodeIfs);
        api.body = data;
        return api;
    }

    // 정상 응답
    public static <T> Api<T> OK(TypeCodeIfs typeCodeIfs) {
        Api<T> api = new Api<T>();
        api.result = Result.OK(typeCodeIfs);
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

    public static Api<Object> CREATE(TypeCodeIfs typeCodeIfs) {
        Api<Object> api = new Api<>();
        api.result = Result.CREATE(typeCodeIfs);
        return api;
    }

    public static <T> Api<T> CREATE(TypeCodeIfs typeCodeIfs, T data) {
        Api<T> api = new Api<T>();
        api.result = Result.CREATE(typeCodeIfs);
        api.body = data;
        return api;
    }

    // 에러 응답
    public static Api<Object> ERROR(TypeCodeIfs typeCodeIfs) {
        Api<Object> api = new Api<Object>();
        api.result = Result.ERROR(typeCodeIfs);
        return api;
    }
}
