package org.ioteatime.meonghanyangserver.groupmember.doamin;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ioteatime.meonghanyangserver.group.domain.GroupEntity;
import org.ioteatime.meonghanyangserver.groupmember.doamin.enums.GroupMemberRole;
import org.ioteatime.meonghanyangserver.member.domain.MemberEntity;

@Data
@Entity
@Table(name = "group_member")
@NoArgsConstructor
@Builder
public class GroupMemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 10)
    @Enumerated(value = EnumType.STRING)
    private GroupMemberRole role;

    @Column(nullable = false, length = 200)
    private String thingId;

    @ManyToOne
    @JoinColumn(nullable = false)
    private GroupEntity group;

    @ManyToOne
    @JoinColumn(nullable = false)
    private MemberEntity member;

    @Builder
    public GroupMemberEntity(
            Long id, GroupMemberRole role, String thingId, GroupEntity group, MemberEntity member) {
        this.id = id;
        this.role = role;
        this.member = member;
        this.thingId = thingId;
        this.group = group;
    }

    public GroupMemberEntity(Long id, GroupMemberRole role, String thingId, MemberEntity member) {
        this.id = id;
        this.role = role;
        this.member = member;
        this.thingId = thingId;
    }

    public static GroupMemberEntity from(
            GroupMemberRole role, String thingId, GroupEntity group, MemberEntity member) {
        return GroupMemberEntity.builder()
                .role(role)
                .thingId(thingId)
                .group(group)
                .member(member)
                .build();
    }
}
