package com.devops.lodgingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PriceByDayDTO {

    private Integer lodgeId;
    private LocalDate date;
    private Double price;
}
