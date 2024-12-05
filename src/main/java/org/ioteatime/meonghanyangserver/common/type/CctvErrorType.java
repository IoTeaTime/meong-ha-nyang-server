package org.ioteatime.meonghanyangserver.common.type;

public enum CctvErrorType implements ErrorTypeCode {
    ALREADY_EXIST("CCTV ALREADY EXIST", "이미 존재하는 CCTV 기기입니다."),
    ONLY_MASTER_CAN_DELETE("BAD REQUEST", "CCTV는 MASTER만 삭제할 수 있습니다."),
    NOT_FOUND("NOT FOUND", "CCTV를 찾을 수 없습니다."),
    INVALID_CCTV_JWT_REQUEST("BAD REQUEST", "CCTV가 요청할 수 없는 잘못된 경로입니다."),
    INVALID_TOKEN("INTERNAL SERVER", "CCTV TOKEN이 만료되었습니다.");

    private final String message;
    private final String description;

    CctvErrorType(String message, String description) {
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
