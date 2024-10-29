package org.ioteatime.meonghanyangserver.group.domain.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum GroupUserRole {
    ROLE_USER("USER"),
    ROLE_MASTER("MASTER");

    private final String description;
}
