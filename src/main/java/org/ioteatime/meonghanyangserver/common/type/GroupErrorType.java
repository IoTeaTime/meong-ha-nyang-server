package org.ioteatime.meonghanyangserver.common.type;

public enum GroupErrorType implements ErrorTypeCode {
    ALREADY_EXISTS("BAD REQUEST", "그룹이 이미 존재합니다."),
    GROUP_MEMBER_ALREADY_EXISTS("BAD REQUEST", "유저가 그룹에 이미 속해 있습니다."),
    NOT_FOUND("NOT FOUND", "그룹이 존재하지 않습니다."),
    ONLY_MASTER_REMOVE_GROUP_MEMBER("BAD REQUEST", "방장만 그룹에서 참여자를 제외시킬 수 있습니다."),
    ONLY_MASTER_REMOVE_GROUP_MASTER("BAD REQUEST", "방장은 자신을 제외시킬 수 있습니다."),
    GROUP_MEMBER_NOT_FOUND("GROUP MEMBER NOT FOUND", "그룹 회원 정보를 찾을 수 없습니다."),
    ONLY_MASTER_GET_GROUP_MEMBER_INFO("BAD REQUEST", "방장만 그룹 참여자 정보를 받을 수 있습니다."),
    ONLY_MASTER_UPDATE_CCTV_NICKNAME("BAD REQUEST", "방장만 그룹의 CCTV 이름을 변경할 수 있습니다.");

    private final String message;
    private final String description;

    GroupErrorType(String message, String description) {
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
