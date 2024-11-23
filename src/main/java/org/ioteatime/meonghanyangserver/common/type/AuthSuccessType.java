package org.ioteatime.meonghanyangserver.common.type;

public enum AuthSuccessType implements SuccessTypeCode {
    SIGN_UP(201, "CREATE", "회원 가입에 성공하였습니다."),
    SEND_EMAIL_CODE(200, "OK", "이메일로 인증 코드 전송에 성공하였습니다."),
    EMAIL_VERIFIED(200, "OK", "회원 이메일 인증에 성공하였습니다."),
    SIGN_IN(200, "OK", "회원 로그인에 성공하였습니다."),
    REISSUE_ACCESS_TOKEN(200, "OK", "ACCESS TOKEN 재발행에 성공하였습니다."),
    GET_USER_DETAIL(200, "OK", "회원 단건 조회에 성공하였습니다."),
    DELETE_USER(200, "OK", "회원 삭제에 성공하였습니다."),
    UPDATE_PASSWORD(200, "OK", "비밀번호 수정에 성공하였습니다."),
    VERIFY_EMAIL_CODE(200, "OK", "이메일 인증 코드 검증에 성공하였습니다."),
    UPDATE_NICKNAME(200, "OK", "닉네임 수정에 성공하였습니다."),
    UPDATE_NICKNAME_AND_GROUP_NAME(200, "OK", "닉네임과 그룹 이름 변경에 성공하였습니다."),
    SIGN_OUT(200, "OK", "로그아웃에 성공하였습니다.");

    private final Integer code;
    private final String message;
    private final String description;

    AuthSuccessType(Integer code, String message, String description) {
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
