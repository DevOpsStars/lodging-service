package com.devops.lodgingservice.service;

import com.devops.lodgingservice.dto.CalculatePriceDTO;
import com.devops.lodgingservice.dto.CalculationResponseDTO;
import com.devops.lodgingservice.dto.PriceByDayDTO;
import com.devops.lodgingservice.model.Availability;
import com.devops.lodgingservice.model.ModificationType;
import com.devops.lodgingservice.model.PriceModification;
import com.devops.lodgingservice.repository.AvailabilityRepository;
import com.devops.lodgingservice.repository.PriceModificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CalculationService {

    @Autowired
    private AvailabilityRepository availabilityRepository;

    @Autowired
    private PriceModificationRepository priceRepository;

    public CalculationResponseDTO calcualtePriceForPeriod(CalculatePriceDTO dto, Double basePrice){
        List<PriceByDayDTO> daysDTOs = new ArrayList<>();

        if(!checkIfLodgeIsAvailableDuringPeriod(dto)){
            return null;
        }
        LocalDate start = LocalDate.parse(dto.getStart());
        LocalDate end = LocalDate.parse(dto.getEnd());
        List<LocalDate> dates = start.datesUntil(end.plusDays(1)).toList();

        for(LocalDate date: dates) {
            System.out.println(basePrice + "CURRENT BASE PRICE");
            // moze vise price modifications
            List<PriceModification> modificationsList =
                    priceRepository.findAllByLodgeIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(dto.getLodgeId(), date, date);
            System.out.println(start + "START DATE");
            System.out.println(end + "END DATE");

//            System.out.println(modificationsList);
            Double finalPrice = applyModifications(modificationsList, basePrice);
            System.out.println(finalPrice + "FINAL PRICE FOR DAY" + date);
            daysDTOs.add(new PriceByDayDTO(dto.getLodgeId(), date, finalPrice));
        }
        Double totalPrice = daysDTOs.stream().map(PriceByDayDTO::getPrice).reduce(0.0, Double::sum);
        System.out.println(totalPrice + "CURRENT TOTAL PRICE");
        return new CalculationResponseDTO(daysDTOs, totalPrice);
    }

    private Double applyModifications(List<PriceModification> modificationsList, Double basePrice) {
        Double price = basePrice;
        for(PriceModification  mod : modificationsList){
            if(ModificationType.DISCOUNT.equals(mod.getModificationType())){
                price-= price * mod.getPercentage() / 100;
            }else { //price increase
                price+= price * mod.getPercentage() / 100;
            }
        }
        return price;
    }

    private Boolean checkIfLodgeIsAvailableDuringPeriod(CalculatePriceDTO dto){
        LocalDate start = LocalDate.parse(dto.getStart());
        LocalDate end = LocalDate.parse(dto.getEnd());
        List<Availability> availabilities = availabilityRepository.findAllByLodgeId(dto.getLodgeId());
        for(Availability a : availabilities){
            if((a.getEndDate().isAfter(end) || a.getEndDate().equals(end))
                    && (a.getStartDate().isBefore(start) || a.getStartDate().equals(start))){

                return true;
            }
        }
        return false;
    }


}
