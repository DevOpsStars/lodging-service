package com.devops.lodgingservice.repository;

import com.devops.lodgingservice.model.Lodge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface LodgeRepository extends JpaRepository<Lodge, Integer>, JpaSpecificationExecutor<Lodge> {
    List<Lodge> findAllByHostId(Integer hostId);
}
