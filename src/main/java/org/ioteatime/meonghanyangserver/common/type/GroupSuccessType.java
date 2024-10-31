package org.ioteatime.meonghanyangserver.common.type;

public enum GroupSuccessType implements SuccessTypeCode {
    CREATE_GROUP(200, "OK", "그룹 생성에 성공하였습니다.");

    private final Integer code;
    private final String message;
    private final String description;

    GroupSuccessType(Integer code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }

    @Override
    public Integer getCode() {
        return this.code;
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
