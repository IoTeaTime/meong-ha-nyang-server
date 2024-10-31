package org.ioteatime.meonghanyangserver.cctv.repository;

public interface CctvRepository {
    boolean existsByKvsChannelName(String kvsChannelName);
}
