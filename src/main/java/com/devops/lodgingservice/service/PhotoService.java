package com.devops.lodgingservice.service;

import com.devops.lodgingservice.dto.PhotoInfoDTO;
import com.devops.lodgingservice.model.Photo;
import com.devops.lodgingservice.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PhotoService {

    @Autowired
    private PhotoRepository photoRepository;

    public Photo savePhoto(Photo photo) throws NullPointerException {
        if (photo == null)
            throw new NullPointerException("Image Data NULL");
        return photoRepository.save(photo);
    }

    public Optional<Photo> findByTitle(String title) {
        return this.photoRepository.findByTitle(title);
    }

    public Optional<Photo> findByFileName(String fileName) {
        return this.photoRepository.findByFileName(fileName);
    }

    public Optional<Photo> findByUuid(String uuid) {
        return this.photoRepository.findByUuid(uuid);
    }

    public List<PhotoInfoDTO> findAllPhotoDescriptions() {
        return photoRepository.findAllPhotoInfo();
    }
}
