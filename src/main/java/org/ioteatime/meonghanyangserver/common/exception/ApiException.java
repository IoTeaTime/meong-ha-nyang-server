package org.team5.interview_partner.common.exception;

import lombok.Getter;
import org.ioteatime.meonghanyangserver.common.error.TypeCodeIfs;

@Getter
public class ApiException extends RuntimeException
        implements org.team5.interview_partner.common.exception.ApiExceptionItf {
    private final TypeCodeIfs typeCodeIfs;
    private final String errorDescription;

    public ApiException(TypeCodeIfs typeCodeIfs) {
        super(typeCodeIfs.getDescription());
        this.typeCodeIfs = typeCodeIfs;
        this.errorDescription = typeCodeIfs.getDescription();
    }

    public ApiException(TypeCodeIfs typeCodeIfs, String errorDescription) {
        super(errorDescription);
        this.typeCodeIfs = typeCodeIfs;
        this.errorDescription = errorDescription;
    }
}
