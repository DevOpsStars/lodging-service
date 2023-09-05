package com.devops.lodgingservice.repository;

import com.devops.lodgingservice.model.PriceModification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface PriceModificationRepository extends JpaRepository<PriceModification, Integer> {
    List<PriceModification> findAllByLodgeId(Integer lodgeId);

//    List<PriceModification> findAllByStartDateLessThanEqualAndEndDateGreaterThanEqual(LocalDate startDate, LocalDate endDate);

//    findAllByStartDateLessThanEqualAndEndDateGreaterThanEqual(OffsetDateTime endDate, OffsetDateTime startDate);
//    @Query(value = "from EntityClassTable t where yourDate BETWEEN :startDate AND :endDate")
//    public List<EntityClassTable> getAllBetweenDates(@Param("startDate")Date startDate,@Param("endDate")Date endDate);
    boolean existsByTitleAndLodgeId(String title, Integer lodgeId);

    List<PriceModification> findAllByLodgeIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(Integer lodgeId, LocalDate startDate, LocalDate endDate);
}
