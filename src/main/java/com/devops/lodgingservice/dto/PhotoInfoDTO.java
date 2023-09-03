package com.devops.lodgingservice.dto;


import com.devops.lodgingservice.model.Photo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PhotoInfoDTO {

    private String uuid;

    private String title;

    private String fileName;

    private String fileType;

    private long size;

    public PhotoInfoDTO(Photo photo) {
        setUuid(photo.getUuid());
        setTitle(photo.getTitle());
        setFileName(photo.getFileName());
        setFileType(photo.getFileType());
        setSize(photo.getSize());
    }
}
