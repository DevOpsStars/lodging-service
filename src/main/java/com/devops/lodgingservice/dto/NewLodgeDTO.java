package com.devops.lodgingservice.dto;

import com.devops.lodgingservice.model.PriceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NewLodgeDTO {

    private String title;
    private Boolean isAutoApproved;
    private Integer hostId;
    private String description;
    private Double basePrice;
    private PriceType priceType;
    private String country;
    private String city;
    private String address;
    private Integer minGuests;
    private Integer maxGuests;
    private Boolean hasKitchen;
    private Boolean hasWifi;
    private Boolean hasAC;
    private Boolean hasFreeParking;
    private Boolean hasBalcony;
}