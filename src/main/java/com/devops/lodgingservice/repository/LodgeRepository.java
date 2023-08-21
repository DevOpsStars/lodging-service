package com.devops.lodgingservice.repository;

import com.devops.lodgingservice.model.Lodge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LodgeRepository extends JpaRepository<Lodge, Integer> {
}
