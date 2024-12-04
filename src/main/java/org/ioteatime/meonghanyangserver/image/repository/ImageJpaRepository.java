package org.ioteatime.meonghanyangserver.image.repository;

import org.ioteatime.meonghanyangserver.image.domain.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageJpaRepository extends JpaRepository<ImageEntity, Long> {}
