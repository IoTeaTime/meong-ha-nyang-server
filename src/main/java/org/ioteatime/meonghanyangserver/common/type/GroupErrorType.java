package org.ioteatime.meonghanyangserver.common.type;

public enum GroupErrorType implements ErrorTypeCode {
    ALREADY_EXISTS("BAD REQUEST", "그룹이 이미 존재합니다."),
    GROUP_USER_NOT_FOUND("NOT FOUND", "그룹 회원 정보를 찾을 수 없습니다."),
    GROUP_NOT_FOUND("NOT FOUND", "그룹 정보를 찾을 수 없습니다.");

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
