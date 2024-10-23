package org.team5.interview_partner.common.exception;

import org.team5.interview_partner.common.error.ErrorCodeIfs;

public interface ApiExceptionItf{
    ErrorCodeIfs getErrorCodeIfs();
    String getErrorDescription();
}
