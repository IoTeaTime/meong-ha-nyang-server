package org.ioteatime.meonghanyangserver.group.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ioteatime.meonghanyangserver.cctv.domain.CctvEntity;
import org.ioteatime.meonghanyangserver.groupmember.doamin.GroupMemberEntity;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "`group`")
@EntityListeners(AuditingEntityListener.class)
public class GroupEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String groupName;

    @Column(nullable = false, length = 100)
    private String fcmTopic;

    @Column @CreatedDate private LocalDateTime createdAt;

    @OneToMany(mappedBy = "group")
    private List<GroupMemberEntity> groupMemberEntities;

    @OneToMany(mappedBy = "group")
    private List<CctvEntity> cctvEntities;

    public GroupEntity updateGroupName(String groupName) {
        this.groupName = groupName;
        return this;
    }
}
