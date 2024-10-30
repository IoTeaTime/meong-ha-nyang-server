package org.ioteatime.meonghanyangserver.auth.dto.reponse;

import jakarta.validation.constraints.NotNull;
import lombok.*;

public record RefreshResponse(@NotNull String accessToken) {}
