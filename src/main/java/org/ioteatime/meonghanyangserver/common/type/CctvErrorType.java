package org.ioteatime.meonghanyangserver.common.type;

public enum CctvErrorType implements ErrorTypeCode {
    ONLY_MASTER_CAN_DELETE("BAD REQUEST", "CCTV는 MASTER만 삭제할 수 있습니다."),
    NOT_FOUND("NOT FOUND", "CCTV를 찾을 수 없습니다.");

    private final String message;
    private final String description;

    CctvErrorType(String message, String description) {
        this.message = message;
        this.description = description;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public String getDescription() {
        return this.description;
    }
}
