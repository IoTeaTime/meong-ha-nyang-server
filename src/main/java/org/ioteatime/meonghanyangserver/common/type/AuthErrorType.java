package org.ioteatime.meonghanyangserver.common.type;

public enum AuthErrorType implements ErrorTypeCode {
    NOT_FOUND("NOT FOUND", "회원을 찾을 수 없습니다."),
    TOKEN_NOT_FOUND("NOT FOUND", "인증 인가 토큰을 찾을 수 없습니다."),
    PASSWORD_NOT_MATCH("BAD REQUEST", "비밀번호가 일치하지 않습니다."),
    REFRESH_TOKEN_INVALID("NOT FOUND", "REFRESH TOKEN이 만료되었거나 유효하지 않습니다."),
    TOKEN_NOT_EQUALS("BAD REQUEST", "토큰이 일치하지 않습니다."),
    HEADER_INVALID("UNAUTHORIZED", "AUTHORIZATION 헤더가 누락되었거나 토큰 형식에 오류가 있습니다."),
    PASSWORD_INVALID("BAD REQUEST", "비밀번호가 유효하지 않습니다."),
    CODE_NOT_EQUALS("BAD REQUEST", "이메일 인증 코드가 일치하지 않습니다."),
    EMAIL_DUPLICATED("BAD REQUEST", "이메일이 중복되었습니다."),
    REFRESH_TOKEN_NOT_FOUND("NOT FOUND", "REFRESH TOKEN을 찾을 수 없습니다."),
    INVALID_AUTH_JWT_REQUEST("BAD REQUEST", "회원이 요청할 수 없는 잘못된 경로입니다.");

    private final String message;
    private final String description;

    AuthErrorType(String message, String description) {
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
