package org.ioteatime.meonghanyangserver.common.type;

public enum FcmErrorType implements ErrorTypeCode {
    IO("IOEXCEPTION", "FCM 서비스 계정 설정 중 IO 오류가 발생하였습니다."),
    SEND_FAILED("SERVER ERROR", "푸시 알림 전송에 실패하였습니다.");

    private final String message;
    private final String description;

    FcmErrorType(String message, String description) {
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
