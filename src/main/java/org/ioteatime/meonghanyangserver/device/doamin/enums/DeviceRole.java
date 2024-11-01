package org.ioteatime.meonghanyangserver.device.doamin.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum DeviceRole {
    ROLE_USER("USER"),
    ROLE_MASTER("MASTER"),
    ROLE_PARTICIPANT("PARTICIPANT"),
    ROLE_CCTV("CCTV");

    private final String description;
}
