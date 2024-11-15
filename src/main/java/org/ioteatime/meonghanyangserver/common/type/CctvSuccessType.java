package org.ioteatime.meonghanyangserver.common.type;

public enum CctvSuccessType implements SuccessTypeCode {
    CREATE_CCTV(201, "CREATE", "CCTV 생성에 성공하였습니다."),
    DELETE_CCTV(200, "OK", "CCTV 삭제(퇴출)에 성공하였습니다.");

    private final Integer code;
    private final String message;
    private final String description;

    CctvSuccessType(Integer code, String message, String description) {
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
