package com.devops.lodgingservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name="price_modification")
public class PriceModification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "price_modification_id")
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "modification_type")
    private ModificationType modificationType;

    @Column(name = "percentage")
    private Integer percentage;

    @ManyToOne(fetch = FetchType.EAGER)
    private Lodge lodge;
}
