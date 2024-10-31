package org.ioteatime.meonghanyangserver.common.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ioteatime.meonghanyangserver.common.error.SuccessTypeCode;
import org.ioteatime.meonghanyangserver.common.error.TypeCode;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    private Integer code;
    private String message;
    private String description;

    // 성공
    public static Result OK() {
        return Result.builder()
                .code(SuccessTypeCode.OK.getCode())
                .message(SuccessTypeCode.OK.getMessage())
                .description("SUCCESS")
                .build();
    }

    // 성공 응답 커스텀
    public static Result OK(TypeCode typeCode) {
        return Result.builder()
                .code(typeCode.getCode())
                .message(typeCode.getMessage())
                .description(typeCode.getDescription())
                .build();
    }

    // 생성 응답
    public static Result CREATE() {
        return Result.builder()
                .code(SuccessTypeCode.CREATE.getCode())
                .message(SuccessTypeCode.CREATE.getMessage())
                .description("SUCCESS")
                .build();
    }

    public static Result CREATE(TypeCode typeCode) {
        return Result.builder()
                .code(typeCode.getCode())
                .message(typeCode.getMessage())
                .description(typeCode.getDescription())
                .build();
    }

    // 에러 응답
    public static Result ERROR(TypeCode typeCode) {
        return Result.builder()
                .code(typeCode.getCode())
                .message(typeCode.getMessage())
                .description("ERROR")
                .build();
    }
}
