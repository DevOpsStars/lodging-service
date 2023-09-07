package com.devops.lodgingservice.controller;

import com.devops.lodgingservice.dto.PhotoInfoDTO;
import com.devops.lodgingservice.model.Photo;
import com.devops.lodgingservice.service.PhotoService;
import com.devops.lodgingservice.util.ImageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/photo")
@Slf4j
@CrossOrigin("*")
public class PhotoController {

    private PhotoService photoService;

    private ImageUtils imageUtils = new ImageUtils();

    @Autowired
    public PhotoController(PhotoService photoService){
        this.photoService = photoService;
    }

    @GetMapping()
    public ResponseEntity<List<PhotoInfoDTO>> getAllPhotoInfos() throws Exception {
        List<PhotoInfoDTO> imageResponses = photoService.findAllPhotoDescriptions();
        return new ResponseEntity<>(imageResponses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PhotoInfoDTO> getPhotoInfoById(@PathVariable Integer id){
        Optional<Photo> result = photoService.findById(id);
        return result.map(photo -> new ResponseEntity<>(new PhotoInfoDTO(photo), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/lodge/{lodgeId}")
    public ResponseEntity<List<PhotoInfoDTO>> getPhotoInfosByLodge(@PathVariable Integer lodgeId){
        List<PhotoInfoDTO> infos = photoService.findByLodgeId(lodgeId);
        return new ResponseEntity<>(infos, HttpStatus.OK);
    }

    @PostMapping("/{lodgeId}/{title}")
    public ResponseEntity<PhotoInfoDTO> uploadSinglePhotoo(@PathVariable Integer lodgeId, @PathVariable String title,
                                                           @RequestParam("file") MultipartFile file) {
        try{
            Photo photo = Photo.buildImage(file, imageUtils);
            Photo saved = photoService.savePhoto(photo, lodgeId, title);
            if(saved != null){
                return new ResponseEntity<>(new PhotoInfoDTO(photo), HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        Boolean result = photoService.deletePhoto(id);
        if(result) {
            return new ResponseEntity<>("Success", HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/show/{fileName}")
    public ResponseEntity<byte[]> getPhoto(@PathVariable String fileName) throws Exception {
        Photo photo = getPhotoByName(fileName);
        return ResponseEntity.ok().contentType(MediaType.valueOf(photo.getFileType())).body(photo.getData());
    }

    @GetMapping("/show/id/{id}")
    public ResponseEntity<byte[]> getPhoto(@PathVariable Integer id) throws Exception {
        Photo photo = getPhotoById(id);
        return ResponseEntity.ok().contentType(MediaType.valueOf(photo.getFileType())).body(photo.getData());
    }

    @GetMapping("/show/{width}/{height}/{fileName:.+}")
    public ResponseEntity<byte[]> getScaledImage(@PathVariable int width, @PathVariable int height,
                                                 @PathVariable String fileName) throws Exception {
        Photo photo = getPhotoByName(fileName, width, height);
        return ResponseEntity.ok().contentType(MediaType.valueOf(photo.getFileType())).body(photo.getData());
    }

    @GetMapping("/show/id/{width}/{height}/{id}")
    public ResponseEntity<byte[]> getScaledImage(@PathVariable int width, @PathVariable int height,
                                                 @PathVariable Integer id) throws Exception {
        Photo photo = getPhotoById(id, width, height);
        return ResponseEntity.ok().contentType(MediaType.valueOf(photo.getFileType())).body(photo.getData());
    }
    public Photo getPhotoById(Integer id) throws Exception {
        Optional<Photo> optionalPhoto = photoService.findById(id);
        if(optionalPhoto.isEmpty()){
            return Photo.defaultImage();
        }
        return optionalPhoto.get();
    }

    public Photo getPhotoById(Integer id, int width, int height) throws Exception {
        Optional<Photo> optionalPhoto = photoService.findById(id);
        if(optionalPhoto.isEmpty()){
            Photo defaultPhoto = Photo.defaultImage();
            defaultPhoto.scale(width, height);
            return defaultPhoto;
        }
        Photo photo = optionalPhoto.get();
        photo.scale(width, height);
        return photo;
    }

    public Photo getPhotoByName(String name) throws Exception {
        Optional<Photo> optionalPhoto = photoService.findByFileName(name);
        if(optionalPhoto.isEmpty()){
            return Photo.defaultImage();
        }
        return optionalPhoto.get();
    }

    public Photo getPhotoByName(String name, int width, int height) throws Exception {
        Optional<Photo> optionalPhoto = photoService.findByFileName(name);
        if (optionalPhoto.isEmpty()) {
            Photo defaultPhoto = Photo.defaultImage();
            defaultPhoto.scale(width, height);
            return defaultPhoto;
        }
        Photo photo = optionalPhoto.get();
        photo.scale(width, height);
        return photo;
    }
}