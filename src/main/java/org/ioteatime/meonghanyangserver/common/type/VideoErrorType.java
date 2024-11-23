package org.ioteatime.meonghanyangserver.common.type;

public enum VideoErrorType implements ErrorTypeCode {
    VIDEO_NOT_FOUND("VIDEO NOT FOUND", "동영상 정보를 찾을 수 없습니다.");

    private final String message;
    private final String description;

    VideoErrorType(String message, String description) {
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
