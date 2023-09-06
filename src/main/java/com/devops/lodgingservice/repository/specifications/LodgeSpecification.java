package com.devops.lodgingservice.repository.specifications;

import com.devops.lodgingservice.dto.LodgeSearchFilterDTO;
import com.devops.lodgingservice.model.Availability;
import com.devops.lodgingservice.model.Lodge;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LodgeSpecification {
    public static Specification<Lodge> getFilteredLodges(LodgeSearchFilterDTO filterDTO) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            Join<Lodge, Availability> lodgeAvailabilityJoin = root.join("lodge");

            List<Predicate> predicates = new ArrayList<>();

            if(filterDTO.getCountry() != null && !filterDTO.getCountry().equals("")){
                predicates.add(criteriaBuilder.like(root.get("country"), '%' + filterDTO.getCountry() + '%'));
            }

            if(filterDTO.getCity() != null && !filterDTO.getCity().equals("")){
                predicates.add(criteriaBuilder.like(root.get("city"), '%' + filterDTO.getCity() + '%'));
            }

            if(filterDTO.getAddress() != null && !filterDTO.getAddress().equals("")){
                predicates.add(criteriaBuilder.like(root.get("address"), '%' + filterDTO.getAddress() + '%'));
            }

            if(filterDTO.getNumOfGuests() != null && !filterDTO.getNumOfGuests().equals(0)){
                Predicate predicateGuestNumMin = criteriaBuilder.greaterThan(root.get("minGuests"), filterDTO.getNumOfGuests());
                Predicate predicateGuestNumMax = criteriaBuilder.lessThan(root.get("maxGuests"), filterDTO.getNumOfGuests());
                predicates.add(criteriaBuilder.and(predicateGuestNumMin, predicateGuestNumMax));
            }

            if(filterDTO.getStartDate() != null && !filterDTO.getStartDate().equals("")){
                try{
                    LocalDate start = LocalDate.parse(filterDTO.getStartDate());
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(lodgeAvailabilityJoin.get("startDate"), start));
                }
                catch(Exception e){
                    System.out.println("No start date after all");
                }
            }

            if(filterDTO.getEndDate() != null && !filterDTO.getEndDate().equals("")){
                try{
                    LocalDate end = LocalDate.parse(filterDTO.getEndDate());
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(lodgeAvailabilityJoin.get("endDate"), end));
                }
                catch(Exception e){
                    System.out.println("No end date after all");
                }
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
