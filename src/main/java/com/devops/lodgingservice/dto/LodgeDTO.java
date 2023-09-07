package com.devops.lodgingservice.dto;

import com.devops.lodgingservice.model.PriceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LodgeDTO {

    private Integer id;
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
    private List<AvailabilityDTO> availabilities;

    private List<PriceModificationDTO> priceModifications;
}
