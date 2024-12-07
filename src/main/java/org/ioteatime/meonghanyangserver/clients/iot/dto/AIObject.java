package org.ioteatime.meonghanyangserver.clients.iot.dto;

import lombok.Getter;

@Getter
public enum AIObject {
    dog("강아지"),
    cat("고양이"),
    person("사람"),
    other("알 수 없는 물체");

    private String value;

    AIObject(String value) {
        this.value = value;
    }
}
