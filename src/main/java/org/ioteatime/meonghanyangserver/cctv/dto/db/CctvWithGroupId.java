package org.ioteatime.meonghanyangserver.cctv.dto.db;

public record CctvWithGroupId(
        Long groupId, Long cctvId, String cctvNickname, String kvsChannelName, String thingId) {}
