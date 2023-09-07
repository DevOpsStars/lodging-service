package com.devops.lodgingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AvailabilityDTO {

    private Integer id;
    private Integer lodgeId;
    private LocalDate start, end;
}
