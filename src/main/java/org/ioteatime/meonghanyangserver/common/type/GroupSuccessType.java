package org.ioteatime.meonghanyangserver.common.type;

public enum GroupSuccessType implements SuccessTypeCode {
    CREATE_GROUP(200, "OK", "그룹 생성에 성공하였습니다."),
    GET_GROUP_MEMBER_INFO(200, "OK", "참여중인 그룹 ID와 역할 조회에 성공하였습니다"),
    GET_GROUP_ID(200, "OK", "참여자 초대를 위한 그룹 ID 조회에 성공하였습니다."),
    GET_CHANNEL_INFO(200, "OK", "그룹 ID와 KVS 채널 이름 조회에 성공하였습니다."),
    GET_GROUP_TOTAL_INFO(200, "OK", "그룹 통합 데이터 조회에 성공하였습니다."),
    DELETE_GROUP_MEMBER(200, "OK", "그룹에서 참여자 제외를 성공하였습니다."),
    JOIN_GROUP_MEMBER(201, "CREATE", "그룹에 참여자 등록을 성공하였습니다"),
    GET_GROUP_MEMBER_INFO_LIST(200, "OK", "그룹 참여자 목록 데이터 조회에 성공하였습니다."),
    UPDATE_GROUP_NAME(200, "OK", "그룹 이름 변경에 성공하였습니다.");

    private final Integer code;
    private final String message;
    private final String description;

    GroupSuccessType(Integer code, String message, String description) {
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
