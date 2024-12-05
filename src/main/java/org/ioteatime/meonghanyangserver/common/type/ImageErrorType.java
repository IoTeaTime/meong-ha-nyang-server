package org.ioteatime.meonghanyangserver.common.type;

public enum ImageErrorType implements ErrorTypeCode {
    NOT_IMAGE_FILE("BAD REQUEST", "이미지 파일 형식이 알맞지 않습니다.");

    private final String message;
    private final String description;

    ImageErrorType(String message, String description) {
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
