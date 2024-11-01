package org.ioteatime.meonghanyangserver.cctv.dto.db;

public record CctvWithDeviceId(Long cctvId, String kvsChannelName, Long deviceId) {}
