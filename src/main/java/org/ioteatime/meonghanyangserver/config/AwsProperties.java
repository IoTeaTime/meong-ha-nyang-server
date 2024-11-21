package org.ioteatime.meonghanyangserver.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "aws")
public record AwsProperties(
        String kvsAccessKey,
        String kvsSecretKey,
        String sesAccessKey,
        String sesSecretKey,
        String iotAccessKey,
        String iotSecretKey,
        String iotEndpoint,
        String iotClientId) {}
