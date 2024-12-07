package org.ioteatime.meonghanyangserver.clients.iot.dto;

public record AIDetectPayload(
        Long trackingId,
        Long timestamp,
        AIObject objectType,
        Double confidence,
        Coordinates coordinates) {}
