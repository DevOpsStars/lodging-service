package com.devops.lodgingservice.dto;

import com.devops.lodgingservice.model.ModificationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PriceModificationDTO {

    private Integer id;

    private String title;

    private Integer lodgeId;

    private LocalDate start;

    private LocalDate end;

    private ModificationType modificationType;

    private Integer percentage;
}
