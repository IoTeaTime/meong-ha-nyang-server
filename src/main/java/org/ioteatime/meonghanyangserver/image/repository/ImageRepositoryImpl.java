package org.ioteatime.meonghanyangserver.image.repository;

import static org.ioteatime.meonghanyangserver.group.domain.QGroupEntity.groupEntity;
import static org.ioteatime.meonghanyangserver.image.domain.QImageEntity.imageEntity;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.ioteatime.meonghanyangserver.image.dto.response.ImageResponse;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ImageRepositoryImpl implements ImageRepository {
    private final JPAQueryFactory queryFactory;
    private final ImageJpaRepository imageJpaRepository;

    @Override
    public List<ImageResponse> findAllByGroupIdAndDate(Long groupId, LocalDateTime searchDate) {
        String dateFormat = "DATE_FORMAT({0}, '%Y.%m.%d.%H:%i')";
        return queryFactory
                .select(
                        Projections.constructor(
                                ImageResponse.class,
                                imageEntity.id.as("imageId"),
                                imageEntity.imageName.as("imageName"),
                                imageEntity.imagePath.as("imagePath"),
                                Expressions.stringTemplate(dateFormat, imageEntity.createdAt)
                                        .as("formattedCreatedAt")))
                .from(imageEntity)
                .join(imageEntity.group, groupEntity)
                .where(
                        imageEntity
                                .group
                                .id
                                .eq(groupId)
                                .and(imageEntity.createdAt.year().eq(searchDate.getYear()))
                                .and(imageEntity.createdAt.month().eq(searchDate.getMonthValue()))
                                .and(
                                        imageEntity
                                                .createdAt
                                                .dayOfMonth()
                                                .eq(searchDate.getDayOfMonth())))
                .orderBy(imageEntity.createdAt.asc())
                .fetch();
    }
}
