package com.devops.lodgingservice.model;

import com.devops.lodgingservice.util.ImageUtils;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "photo")
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "photo_id")
    private Integer id;

    @Column(name = "title")
    private String title;

    @ManyToOne(fetch = FetchType.EAGER)
    private Lodge lodge;

//    @Lob
//    @Column(name = "image_data", length = 1000)
//    private byte[] imageData;
//
    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_type")
    private String fileType;

    @Column(name = "size")
    private long size;

    @Column(name = "uuid")
    private String uuid;

    @Lob
    @Column(name = "data")
    private byte[] data;

    public static Photo buildImage(MultipartFile file, ImageUtils helper) throws Exception {
        String fileName = helper.generateDisplayName(file.getOriginalFilename());

        Photo photo = Photo.build();
        photo.setFileName(fileName);
        photo.setFiles(file);

        try {
            photo.setData(file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return photo;
    }

    @Transient
    public byte[] scale(int width, int height) throws Exception {

        if (width == 0 || height == 0)
            return data;

        ByteArrayInputStream in = new ByteArrayInputStream(data);

        try {
            BufferedImage img = ImageIO.read(in);

            java.awt.Image scaledImage = img.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);

            BufferedImage imgBuff = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            imgBuff.getGraphics().drawImage(scaledImage, 0, 0, new Color(0, 0, 0), null);

            ByteArrayOutputStream buffer = new ByteArrayOutputStream();

            ImageIO.write(imgBuff, "jpg", buffer);
            setData(buffer.toByteArray());

            return buffer.toByteArray();

        } catch (Exception e) {
            throw new Exception("IOException in scale");
        }
    }
    // todo izbaciti default image?
    private static InputStream getResourceFileAsInputStream(String fileName) {
        ClassLoader classLoader = Photo.class.getClassLoader();
        return classLoader.getResourceAsStream(fileName);
    }

    @Transient
    public static Photo defaultImage() throws Exception {
        InputStream is = getResourceFileAsInputStream("notfound.jpg");
        String fileType = "image/jpeg";
        byte[] bdata = FileCopyUtils.copyToByteArray(is);
        Photo photo = new Photo(null, "", null, "", fileType, 0, null, bdata);
        return photo;
    }

    @Transient
    public static Photo defaultImage(int width, int height) throws Exception {
        Photo defaultImage = defaultImage();
        defaultImage.scale(width, height);
        return defaultImage;
    }
    @Transient
    public static Photo build() {
        String uuid = UUID.randomUUID().toString();
        Photo photo = new Photo();
        photo.setUuid(uuid);
        return photo;
    }

    @Transient
    public void setFiles(MultipartFile file) {
        setFileType(file.getContentType());
        setSize(file.getSize());
    }
}
