package com.devops.lodgingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LodgeSearchFilterDTO {

    private String country;
    private String city;
    private String address;
    private Integer numOfGuests;
    private String startDate;
    private String endDate;

    private int page;
    private int pageSize;
}
