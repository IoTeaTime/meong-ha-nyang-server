package org.ioteatime.meonghanyangserver.group.repository;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.ioteatime.meonghanyangserver.group.domain.GroupEntity;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class GroupRepositoryImpl implements GroupRepository {
    private final JpaGroupRepository jpaGroupRepository;

    @Override
    public GroupEntity save(GroupEntity groupEntity) {
        return jpaGroupRepository.save(groupEntity);
    }

    @Override
    public Optional<GroupEntity> findById(Long groupId) {
        return jpaGroupRepository.findById(groupId);
    }
}
