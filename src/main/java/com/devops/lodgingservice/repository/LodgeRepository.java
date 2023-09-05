package com.devops.lodgingservice.repository;

import com.devops.lodgingservice.model.Lodge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LodgeRepository extends JpaRepository<Lodge, Integer> {
    List<Lodge> findAllByHostId(Integer hostId);
}
