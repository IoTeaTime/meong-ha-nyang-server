package org.team5.interview_partner.common.exception;

import org.ioteatime.meonghanyangserver.common.error.TypeCodeIfs;

public interface ApiExceptionItf {
    TypeCodeIfs getTypeCodeIfs();
    String getErrorDescription();
}