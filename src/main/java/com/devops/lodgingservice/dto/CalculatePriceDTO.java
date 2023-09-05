package com.devops.lodgingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CalculatePriceDTO {

    private Integer lodgeId;

    private Integer numOfGuests;

    private String start;

    private String end;
}
