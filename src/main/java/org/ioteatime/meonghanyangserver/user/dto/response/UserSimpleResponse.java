package org.ioteatime.meonghanyangserver.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSimpleResponse {
    private Long id;
    private String email;
//    ID랑 Email을 DTO에서 받아 보내줘야함.
}
