package org.ioteatime.meonghanyangserver.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDto {
    @NotBlank private String email;
    @NotBlank private String password;
}
