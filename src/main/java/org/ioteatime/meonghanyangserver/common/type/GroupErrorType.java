package org.ioteatime.meonghanyangserver.common.type;

public enum GroupErrorType implements ErrorTypeCode {
    ALREADY_EXSIST("BAD REQUEST", "그룹이 이미 존재합니다.");

    private final String message;
    private final String description;

    GroupErrorType(String message, String description) {
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
