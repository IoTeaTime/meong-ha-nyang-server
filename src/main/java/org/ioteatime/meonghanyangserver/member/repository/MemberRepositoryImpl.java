package org.ioteatime.meonghanyangserver.member.repository;

import static org.ioteatime.meonghanyangserver.groupmember.doamin.QGroupMemberEntity.groupMemberEntity;
import static org.ioteatime.meonghanyangserver.member.domain.QMemberEntity.memberEntity;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.ioteatime.meonghanyangserver.auth.dto.db.LoginWithMemberInfo;
import org.ioteatime.meonghanyangserver.member.domain.MemberEntity;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {
    private final JpaMemberRepository jpaMemberRepository;
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<MemberEntity> findById(Long memberId) {
        return jpaMemberRepository.findById(memberId);
    }

    @Override
    public Optional<MemberEntity> findByEmail(String email) {
        return jpaMemberRepository.findByEmail(email);
    }

    @Override
    public void deleteById(Long memberId) {
        jpaMemberRepository.deleteById(memberId);
    }

    @Override
    public MemberEntity save(MemberEntity memberEntity) {
        return jpaMemberRepository.save(memberEntity);
    }

    @Override
    public Optional<MemberEntity> updateFcmTokenById(Long memberId, String token) {
        return jpaMemberRepository.findById(memberId).map(entity -> entity.updateFcmToken(token));
    }

    @Override
    public Optional<LoginWithMemberInfo> findGroupMemberInfoByEmail(String email) {
        return Optional.ofNullable(
                queryFactory
                        .select(
                                Projections.constructor(
                                        LoginWithMemberInfo.class,
                                        memberEntity.id.as("memberId"),
                                        memberEntity.nickname.as("nickname"),
                                        memberEntity.password.as("password"),
                                        groupMemberEntity.id.isNotNull().as("isGroupMember"),
                                        groupMemberEntity.role.as("role")))
                        .from(memberEntity)
                        .leftJoin(groupMemberEntity)
                        .on(groupMemberEntity.member.eq(memberEntity))
                        .where(memberEntity.email.eq(email))
                        .fetchOne());
    }
}
