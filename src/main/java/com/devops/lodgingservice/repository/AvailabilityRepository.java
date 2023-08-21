package com.devops.lodgingservice.repository;

import com.devops.lodgingservice.model.Availability;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvailabilityRepository extends JpaRepository<Availability, Integer> {
}
