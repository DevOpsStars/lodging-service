package com.devops.lodgingservice.dto;

import com.devops.lodgingservice.model.ModificationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NewPriceModificationDTO {

    private Integer lodgeId;
    private String title;
    private String start;
    private String end;
    private ModificationType modificationType;
    private Integer percentage;
}
