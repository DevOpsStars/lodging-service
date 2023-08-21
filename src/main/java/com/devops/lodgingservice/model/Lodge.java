package com.devops.lodgingservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name="lodge")
public class Lodge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lodge_id")
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "base_price")
    private Double basePrice;

    @Column(name = "price_type")
    private PriceType priceType;

    @Column(name = "country")
    private String country;

    @Column(name = "city")
    private String city;

    @Column(name = "address")
    private String address;

    @Column(name = "minGuests")
    private Integer minGuests;

    @Column(name = "maxGuests")
    private Integer maxGuests;

    @Column(name = "hasKitchen")
    private Boolean hasKitchen;

    @Column(name = "hasWifi")
    private Boolean hasWifi;

    @Column(name = "hasAC")
    private Boolean hasAC;

    @Column(name = "hasFreeParking")
    private Boolean hasFreeParking;

    @Column(name = "hasBalcony")
    private Boolean hasBalcony;

    @OneToMany(mappedBy = "lodge", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Photo> photos;

    @OneToMany(mappedBy = "lodge", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Availability> availabilities;

    @OneToMany(mappedBy = "lodge", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<PriceModification> priceModifications;
}
