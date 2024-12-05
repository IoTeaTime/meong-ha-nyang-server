package org.ioteatime.meonghanyangserver.common.type;

public enum ImageSuccessType implements SuccessTypeCode {
    GET_LIST_OF_DATE(200, "OK", "날짜에 해당하는 이미지 목록 조회에 성공하였습니다."),
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
