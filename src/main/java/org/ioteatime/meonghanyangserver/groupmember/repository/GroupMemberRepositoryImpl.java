package org.ioteatime.meonghanyangserver.groupmember.repository;

import static org.ioteatime.meonghanyangserver.groupmember.doamin.QGroupMemberEntity.groupMemberEntity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.ioteatime.meonghanyangserver.group.domain.GroupEntity;
import org.ioteatime.meonghanyangserver.groupmember.doamin.GroupMemberEntity;
import org.ioteatime.meonghanyangserver.groupmember.doamin.QGroupMemberEntity;
import org.ioteatime.meonghanyangserver.groupmember.doamin.enums.GroupMemberRole;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class GroupMemberRepositoryImpl implements GroupMemberRepository {
    private final JpaGroupMemberRepository jpaGroupMemberRepository;

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public GroupMemberEntity save(GroupMemberEntity groupMemberEntity) {
        return jpaGroupMemberRepository.save(groupMemberEntity);
    }

    @Override
    public boolean existsGroupMember(Long memberId) {
        return jpaGroupMemberRepository.existsByMemberId(memberId);
    }

    @Override
    public Optional<GroupMemberEntity> findByMemberId(Long memberId) {
        return jpaGroupMemberRepository.findByMemberId(memberId);
    }

    @Override
    public GroupEntity findGroupMember(Long memberId) {
        return jpaQueryFactory
                .select(groupMemberEntity.group)
                .from(groupMemberEntity)
                .where(groupMemberEntity.member.id.eq(memberId))
                .fetchOne();
    }

    @Override
    public void deleteById(Long id) {
        jpaGroupMemberRepository.deleteById(id);
    }

    @Override
    public boolean isMasterMember(Long memberId) {
        return !jpaQueryFactory
                .select(groupMemberEntity.role)
                .from(groupMemberEntity)
                .where(
                        groupMemberEntity
                                .member
                                .id
                                .eq(memberId)
                                .and(groupMemberEntity.role.eq(GroupMemberRole.ROLE_MASTER)))
                .fetch()
                .isEmpty();
    }

    @Override
    public Optional<GroupMemberEntity> findByGroupIdAndMemberIdAndRole(
            Long memberId, Long groupId) {
        return Optional.ofNullable(
                jpaQueryFactory
                        .selectFrom(groupMemberEntity)
                        .where(
                                groupMemberEntity
                                        .group
                                        .id
                                        .eq(groupId)
                                        .and(
                                                groupMemberEntity
                                                        .member
                                                        .id
                                                        .eq(memberId)
                                                        .and(
                                                                groupMemberEntity.role.eq(
                                                                        GroupMemberRole
                                                                                .ROLE_MASTER))))
                        .fetchOne());
    }

    @Override
    public void deleteByGroupIdAndMemberId(Long groupId, Long memberId) {
        jpaGroupMemberRepository.deleteByGroupIdAndMemberId(groupId, memberId);
    }

    @Override
    public Optional<GroupMemberEntity> findByGroupIdAndMemberId(Long groupId, Long memberId) {
        return Optional.ofNullable(
                jpaQueryFactory
                        .selectFrom(groupMemberEntity)
                        .where(
                                groupMemberEntity
                                        .group
                                        .id
                                        .eq(groupId)
                                        .and(groupMemberEntity.member.id.eq(memberId)))
                        .fetchOne());
    }

    @Override
    public void deleteByGroupId(Long groupId) {
        jpaGroupMemberRepository.deleteByGroupId(groupId);
    }

    @Override
    public Optional<GroupMemberEntity> findByMemberIdAndGroupId(Long memberId, Long groupId) {
        QGroupMemberEntity groupMember = QGroupMemberEntity.groupMemberEntity;

        GroupMemberEntity result =
                jpaQueryFactory
                        .selectFrom(groupMember)
                        .where(
                                groupMember
                                        .member
                                        .id
                                        .eq(memberId)
                                        .and(groupMember.group.id.eq(groupId)))
                        .fetchOne();

        return Optional.ofNullable(result);
    }
}
