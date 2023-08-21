package com.devops.lodgingservice.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;

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

    @Lob
    @Column(name = "image_data", length = 1000)
    private byte[] imageData;

}
