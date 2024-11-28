package org.ioteatime.meonghanyangserver.common.slack;

public enum SlackEnum {
    ERROR_CHANNEL("#서버-에러-보고");

    private final String channel;

    SlackEnum(String channel) {
        this.channel = channel;
    }

    public String getValue() {
        return this.channel;
    }
}
