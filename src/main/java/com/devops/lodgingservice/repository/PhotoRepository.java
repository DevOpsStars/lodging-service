package com.devops.lodgingservice.repository;

import com.devops.lodgingservice.dto.PhotoInfoDTO;
import com.devops.lodgingservice.model.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PhotoRepository extends JpaRepository<Photo, Integer> {


    Optional<Photo> findByUuid(String uuid);

    Optional<Photo> findByTitle(String title);

    Optional<Photo> findByFileName(String fileName);

    @Query(value = "select new com.devops.lodgingservice.dto.PhotoInfoDTO(p.uuid, p.title, p.fileName, p.fileType, p.size) from com.devops.lodgingservice.model.Photo p", nativeQuery = false)
    List<PhotoInfoDTO> findAllPhotoInfo();


}
