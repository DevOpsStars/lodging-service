package com.devops.lodgingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NewAvailabilityDTO {

    private Integer lodgeId;
    private String start;
    private String end;
}
