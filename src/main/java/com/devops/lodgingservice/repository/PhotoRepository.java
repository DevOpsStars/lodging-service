package com.devops.lodgingservice.repository;

import com.devops.lodgingservice.model.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PhotoRepository extends JpaRepository<Photo, Integer> {

    Optional<Photo> findByTitle(String title);
}
