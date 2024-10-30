package org.ioteatime.meonghanyangserver.group.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ioteatime.meonghanyangserver.group.domain.enums.GroupUserRole;
import org.ioteatime.meonghanyangserver.user.domain.UserEntity;

@Data
@Entity
@Table(name = "group_user")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupUserEntity {
    @EmbeddedId private GroupUserId id;

    @Column(nullable = false, length = 10)
    @Enumerated(value = EnumType.STRING)
    private GroupUserRole role;

    @ManyToOne
    @MapsId("groupId")
    @JoinColumn(name = "group_id")
    private GroupEntity group;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id", unique = true)
    private UserEntity user;
}
