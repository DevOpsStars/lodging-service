package com.devops.lodgingservice.repository;

import com.devops.lodgingservice.model.PriceModification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceModificationRepository extends JpaRepository<PriceModification, Integer> {

}
