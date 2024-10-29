package org.ioteatime.meonghanyangserver.group.domain;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class GroupUserId implements Serializable {
    private Long groupId;
    private Long userId;
}