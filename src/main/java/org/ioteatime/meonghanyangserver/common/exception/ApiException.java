package org.ioteatime.meonghanyangserver.common.exception;

import org.ioteatime.meonghanyangserver.common.type.ErrorTypeCode;
import org.springframework.http.HttpStatus;

public interface ApiException {
    ErrorTypeCode getTypeCode();

    HttpStatus getHttpStatus();
}
