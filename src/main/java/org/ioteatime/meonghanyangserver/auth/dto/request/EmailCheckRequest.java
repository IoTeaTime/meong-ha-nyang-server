package org.ioteatime.meonghanyangserver.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class EmailCheckRequest {

    @NotBlank private String email;
}
