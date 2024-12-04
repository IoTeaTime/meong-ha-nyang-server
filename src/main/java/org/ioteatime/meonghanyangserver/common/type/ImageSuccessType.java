package org.ioteatime.meonghanyangserver.common.type;

public enum ImageSuccessType implements SuccessTypeCode {
    CREATE_PRESIGNED_URL(200, "OK", "Presigned Url 생성에 성공하였습니다.");

    private final Integer code;
    private final String message;
    private final String description;

    ImageSuccessType(Integer code, String message, String description) {
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
