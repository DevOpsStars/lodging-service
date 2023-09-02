package com.devops.lodgingservice.repository;

import com.devops.lodgingservice.model.PriceModification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PriceModificationRepository extends JpaRepository<PriceModification, Integer> {
    List<PriceModification> findAllByLodgeId(Integer lodgeId);

    boolean existsByTitleAndLodgeId(String title, Integer lodgeId);
}
