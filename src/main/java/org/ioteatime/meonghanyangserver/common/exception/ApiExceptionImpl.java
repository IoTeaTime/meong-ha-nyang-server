package org.ioteatime.meonghanyangserver.common.exception;

import lombok.Getter;
import org.ioteatime.meonghanyangserver.common.error.TypeCode;

@Getter
public class ApiExceptionImpl extends RuntimeException implements ApiException {
    private final TypeCode typeCode;
    private final String errorDescription;

    public ApiExceptionImpl(TypeCode typeCode) {
        super(typeCode.getDescription());
        this.typeCode = typeCode;
        this.errorDescription = typeCode.getDescription();
    }

    public ApiExceptionImpl(TypeCode typeCode, String errorDescription) {
        super(errorDescription);
        this.typeCode = typeCode;
        this.errorDescription = errorDescription;
    }
}
