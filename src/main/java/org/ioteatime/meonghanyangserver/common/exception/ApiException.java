package org.ioteatime.meonghanyangserver.common.exception;

import org.ioteatime.meonghanyangserver.common.error.TypeCode;

public interface ApiException {
    TypeCode getTypeCode();

    String getErrorDescription();
}
