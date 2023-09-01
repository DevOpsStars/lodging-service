package com.devops.lodgingservice.repository;

import com.devops.lodgingservice.model.Availability;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AvailabilityRepository extends JpaRepository<Availability, Integer> {
    List<Availability> findAllByLodgeId(Integer lodgeId);
}
