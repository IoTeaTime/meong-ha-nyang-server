package org.ioteatime.meonghanyangserver.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SuccessTypeCode implements TypeCodeIfs {
    // 성공
    OK(200, "OK", "success"),
    // 생성
    CREATE(201, "CREATE", "success");

    private final Integer code;
    private final String message;
    private final String description;
}
