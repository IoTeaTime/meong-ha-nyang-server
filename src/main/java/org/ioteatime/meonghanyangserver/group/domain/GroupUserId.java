package org.ioteatime.meonghanyangserver.group.domain;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class GroupUserId implements Serializable {
    private Long groupId;
    private Long userId;
}
