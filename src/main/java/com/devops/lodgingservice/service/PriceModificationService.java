package com.devops.lodgingservice.service;

import com.devops.lodgingservice.dto.NewPriceModificationDTO;
import com.devops.lodgingservice.dto.PriceModificationDTO;
import com.devops.lodgingservice.model.Availability;
import com.devops.lodgingservice.model.Lodge;
import com.devops.lodgingservice.model.PriceModification;
import com.devops.lodgingservice.repository.AvailabilityRepository;
import com.devops.lodgingservice.repository.LodgeRepository;
import com.devops.lodgingservice.repository.PriceModificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PriceModificationService {

    @Autowired
    private PriceModificationRepository priceRepository;

    @Autowired
    private AvailabilityRepository availabilityRepository;

    @Autowired
    private LodgeRepository lodgeRepository;

    public List<PriceModification> getAll() {
        return priceRepository.findAll();
    }

    public Optional<PriceModification> getById(Integer id) {
        return priceRepository.findById(id);
    }
    public List<PriceModification> getAllByLodgeId(Integer lodgeId) {
        return priceRepository.findAllByLodgeId(lodgeId);
    }

    public PriceModification createNew(NewPriceModificationDTO dto) {
        if(lodgeRepository.existsById(dto.getLodgeId())
                && !priceRepository.existsByTitleAndLodgeId(dto.getTitle(), dto.getLodgeId())
                && checkIfDatesAreValid(dto)) {

            PriceModification newPriceModification = newPriceModificationDTOToPriceModification(dto);
            if(newPriceModification != null) {
                return priceRepository.save(newPriceModification);
            }
        }
        return null;
    }

    private Boolean checkIfDatesAreValid(NewPriceModificationDTO dto) {
        // check if price modification date range is inside an availability
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

    public PriceModification updatePriceModification(Integer id, NewPriceModificationDTO dto) {
        LocalDate start = LocalDate.parse(dto.getStart());
        LocalDate end = LocalDate.parse(dto.getEnd());
        if(priceRepository.existsById(id)
                && lodgeRepository.existsById(dto.getLodgeId())
                && !priceRepository.existsByTitleAndLodgeId(dto.getTitle(), dto.getLodgeId())){

            PriceModification priceModification = priceRepository.findById(id).get();
            priceModification.setTitle(dto.getTitle());
            priceModification.setModificationType(dto.getModificationType());
            priceModification.setPercentage(dto.getPercentage());
            priceModification.setStartDate(start);
            priceModification.setEndDate(end);

            return priceRepository.save(priceModification);
        }
        return null;
    }

    public Boolean deletePriceModification(Integer id) {
        Optional<PriceModification> result = priceRepository.findById(id);
        if(result.isPresent()){
            priceRepository.delete(result.get());
            return true;
        }
        return false;
    }


    public PriceModificationDTO priceModificationToPriceModificationDTO(PriceModification p) {
        PriceModificationDTO dto = new PriceModificationDTO();
        dto.setId(p.getId());
        dto.setTitle(p.getTitle());
        dto.setModificationType(p.getModificationType());
        dto.setPercentage(p.getPercentage());
        dto.setLodgeId(p.getLodge().getId());
        dto.setStart(p.getStartDate());
        dto.setEnd(p.getEndDate());

        return dto;
    }

    private PriceModification newPriceModificationDTOToPriceModification(NewPriceModificationDTO dto) {
        LocalDate start = LocalDate.parse(dto.getStart());
        LocalDate end = LocalDate.parse(dto.getEnd());
        PriceModification price = new PriceModification();
        Optional<Lodge> optional = lodgeRepository.findById(dto.getLodgeId());
        if(optional.isEmpty()){
            return null;
        }

        price.setTitle(dto.getTitle());
        price.setModificationType(dto.getModificationType());
        price.setPercentage(dto.getPercentage());
        price.setLodge(optional.get());
        price.setStartDate(start);
        price.setEndDate(end);

        return price;
    }











}
