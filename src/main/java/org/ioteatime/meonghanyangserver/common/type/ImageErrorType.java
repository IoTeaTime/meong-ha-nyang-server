package org.ioteatime.meonghanyangserver.common.type;

public enum ImageErrorType implements ErrorTypeCode {
    IMAGE_NOT_FOUND("IMAGE NOT FOUND", "이미지 정보를 찾을 수 없습니다.");

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
