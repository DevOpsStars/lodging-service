package com.devops.lodgingservice.dto;


import com.devops.lodgingservice.model.Photo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PhotoInfoDTO {

    private Integer id;
    private String uuid;
    private String title;
    private Integer lodgeId;
    private String fileName;
    private String fileType;
    private long size;

    public PhotoInfoDTO(Photo photo) {
        setId(photo.getId());
        setUuid(photo.getUuid());
        setTitle(photo.getTitle());
        setLodgeId(photo.getLodge().getId());
        setFileName(photo.getFileName());
        setFileType(photo.getFileType());
        setSize(photo.getSize());
    }
}
