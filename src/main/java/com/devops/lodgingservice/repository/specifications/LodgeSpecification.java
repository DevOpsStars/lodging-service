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
            Join<Availability, Lodge> availabilityLodgeJoin = root.join("availabilities");

            List<Predicate> predicates = new ArrayList<>();
            if(filterDTO.getCountry() != null && !filterDTO.getCountry().equals("")){
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("country")),'%' + filterDTO.getCountry().toLowerCase() + '%'));
            }

            if(filterDTO.getCity() != null && !filterDTO.getCity().equals("")){
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("city")), '%' + filterDTO.getCity().toLowerCase() + '%'));
            }

            if(filterDTO.getAddress() != null && !filterDTO.getAddress().equals("")){
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("address")), '%' + filterDTO.getAddress().toLowerCase() + '%'));
            }

            if(filterDTO.getNumOfGuests() != null && !filterDTO.getNumOfGuests().equals(0)){
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("minGuests"), filterDTO.getNumOfGuests()));
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("maxGuests"), filterDTO.getNumOfGuests()));
            }

            if((filterDTO.getStartDate() != null && !filterDTO.getStartDate().equals(""))){
                try{
                    LocalDate start = LocalDate.parse(filterDTO.getStartDate());
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(availabilityLodgeJoin.get("startDate"), start));
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(availabilityLodgeJoin.get("endDate"), start));
                }
                catch(Exception e){
                    System.out.println("No start date after all");
                }
            }

            if(filterDTO.getEndDate() != null && !filterDTO.getEndDate().equals("")){
                try{
                    LocalDate end = LocalDate.parse(filterDTO.getEndDate());
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(availabilityLodgeJoin.get("startDate"), end));
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(availabilityLodgeJoin.get("endDate"), end));
                }
                catch(Exception e){
                    System.out.println("No end date after all");
                }
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
