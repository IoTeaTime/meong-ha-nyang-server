package org.ioteatime.meonghanyangserver.group.domain;

import jakarta.persistence.*;
import lombok.Data;
import org.ioteatime.meonghanyangserver.user.domain.UserEntity;

@Data
@Entity
@Table(name = "group_user")
public class GroupUserEntity {
    @EmbeddedId
    private GroupUserId id;

    @Column(nullable = false, length = 10)
    private String role;

    @ManyToOne
    @MapsId("groupId")
    @JoinColumn(name = "group_id")
    private GroupEntity group;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private UserEntity user;
}