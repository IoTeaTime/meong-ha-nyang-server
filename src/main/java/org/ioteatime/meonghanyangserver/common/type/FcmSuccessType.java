package org.ioteatime.meonghanyangserver.common.type;

public enum FcmSuccessType implements SuccessTypeCode {
    SAVE(200, "OK", "FCM 토큰 저장에 성공하였습니다");

    private final Integer code;
    private final String message;
    private final String description;

    FcmSuccessType(Integer code, String message, String description) {
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
