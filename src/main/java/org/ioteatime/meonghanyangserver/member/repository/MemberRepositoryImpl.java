package org.ioteatime.meonghanyangserver.member.repository;

import static org.ioteatime.meonghanyangserver.member.domain.QMemberEntity.memberEntity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
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
}
