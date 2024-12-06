package org.ioteatime.meonghanyangserver.common.type;

public enum IoTErrorType implements ErrorTypeCode {
    UPDATE_SHADOW("INTERNAL_SERVER", "Shadow 갱신에 실패하였습니다."),
    TOPIC_NULL("BAD_REQUEST", "Topic이 Null입니다.");

    private final String message;
    private final String description;

    IoTErrorType(String message, String description) {
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
