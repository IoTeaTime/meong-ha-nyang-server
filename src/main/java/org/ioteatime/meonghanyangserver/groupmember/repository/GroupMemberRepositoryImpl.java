package org.ioteatime.meonghanyangserver.groupmember.repository;

// import static org.ioteatime.meonghanyangserver.groupmember.doamin.QDeviceEntity.deviceEntity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.ioteatime.meonghanyangserver.group.domain.GroupEntity;
import org.ioteatime.meonghanyangserver.groupmember.doamin.GroupMemberEntity;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class GroupMemberRepositoryImpl implements GroupMemberRepository {
    private final JpaGroupMemberRepository jpaGroupMemberRepository;

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public GroupMemberEntity createGroupMember(GroupMemberEntity groupMemberEntity) {
        return jpaGroupMemberRepository.save(groupMemberEntity);
    }

    @Override
    public boolean existsGroupMember(Long memberId) {
        return jpaGroupMemberRepository.existsByMemberId(memberId);
    }

    @Override
    public Optional<GroupMemberEntity> findByDeviceId(Long memberId) {
        return jpaGroupMemberRepository.findByMemberId(memberId);
    }

    @Override
    public GroupEntity findDevice(Long memberId) {
        //        return jpaQueryFactory
        //                .select(deviceEntity.group)
        //                .from(deviceEntity)
        //                .where(deviceEntity.member.id.eq(memberId))
        //                .fetchOne();
        return null;
    }

    @Override
    public boolean isParcitipantUserId(Long userId) {
        //        return !jpaQueryFactory
        //                .select(deviceEntity.role)
        //                .from(deviceEntity)
        //                .where(
        //                        deviceEntity
        //                                .member
        //                                .id
        //                                .eq(userId)
        //                                .and(deviceEntity.role.eq(DeviceRole.ROLE_PARTICIPANT)))
        //                .fetch()
        //                .isEmpty();
        return true;
    }

    @Override
    public void deleteById(Long id) {
        jpaGroupMemberRepository.deleteById(id);
    }

    @Override
    public GroupMemberEntity save(GroupMemberEntity device) {
        return jpaGroupMemberRepository.save(device);
    }
}
