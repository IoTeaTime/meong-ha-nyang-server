package org.ioteatime.meonghanyangserver.common.exception;

import org.ioteatime.meonghanyangserver.common.error.TypeCodeIfs;

public interface ApiExceptionItf {
    TypeCodeIfs getTypeCodeIfs();

    String getErrorDescription();
}
