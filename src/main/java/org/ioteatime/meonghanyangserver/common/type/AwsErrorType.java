package org.ioteatime.meonghanyangserver.common.type;

public enum AwsErrorType implements ErrorTypeCode {
    KVS_CHANNEL_NAME_NOT_FOUND("NOT FOUND", "채널 이름에 해당하는 KVS 채널을 찾을 수 없습니다.");

    private final String message;
    private final String description;

    AwsErrorType(String message, String description) {
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
