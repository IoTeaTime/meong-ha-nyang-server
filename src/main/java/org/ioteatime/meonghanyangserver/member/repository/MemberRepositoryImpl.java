package org.ioteatime.meonghanyangserver.member.repository;

// import static org.ioteatime.meonghanyangserver.groupmember.doamin.QDeviceEntity.deviceEntity;
import static org.ioteatime.meonghanyangserver.member.domain.QMemberEntity.memberEntity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.ioteatime.meonghanyangserver.member.domain.MemberEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public void deleteById(Long memberId) {
        //
        // queryFactory.delete(deviceEntity).where(deviceEntity.member.id.eq(memberId)).execute();

        queryFactory.delete(memberEntity).where(memberEntity.id.eq(memberId)).execute();
    }

    @Override
    public MemberEntity save(MemberEntity memberEntity) {
        return jpaMemberRepository.save(memberEntity);
    }
}
