package com.devops.lodgingservice.service;

import com.devops.lodgingservice.dto.AvailabilityDTO;
import com.devops.lodgingservice.dto.NewAvailabilityDTO;
import com.devops.lodgingservice.model.Availability;
import com.devops.lodgingservice.model.Lodge;
import com.devops.lodgingservice.repository.AvailabilityRepository;
import com.devops.lodgingservice.repository.LodgeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AvailabilityService {

    @Autowired
    private AvailabilityRepository availabilityRepository;

    @Autowired
    private LodgeRepository lodgeRepository;

    public List<Availability> getAll() {
        return availabilityRepository.findAll();
    }

    public Optional<Availability> getById(Integer id) {
        return availabilityRepository.findById(id);
    }

    public Availability createNew(NewAvailabilityDTO dto) {
        LocalDate start = LocalDate.parse(dto.getStart());
        LocalDate end = LocalDate.parse(dto.getEnd());
        if(lodgeRepository.existsById(dto.getLodgeId()) && !isOverlapPresent(dto.getLodgeId(), start, end, 0)){
            Availability newAvailability = convertNewAvailabilityDTOToAvailability(dto);
            return availabilityRepository.save(newAvailability);
        }
        return null;
    }

    private Boolean isOverlapPresent(Integer lodgeId, LocalDate start, LocalDate end, Integer excluding){
        List<Availability> all = availabilityRepository.findAllByLodgeId(lodgeId);
        if(all.size() == 0){
            return false;
        }
        for(Availability a: all){
            if(Objects.equals(a.getId(), excluding)){
                continue;
            }
            boolean overlaps = (a.getStartDate().isBefore(end)) && (a.getEndDate().isAfter(start));
            if(overlaps) {
                return true;
            }
        }
        return false;
    }

    public Availability updateAvailability(Integer id, NewAvailabilityDTO dto) {
        LocalDate start = LocalDate.parse(dto.getStart());
        LocalDate end = LocalDate.parse(dto.getEnd());
        if(availabilityRepository.existsById(id)
                && lodgeRepository.existsById(dto.getLodgeId())
                && !isOverlapPresent(dto.getLodgeId(), start, end, id)){

            Optional<Availability> optional = availabilityRepository.findById(id);
            if(optional.isEmpty()){
                return null;
            }
            Availability availability = optional.get();
            availability.setStartDate(start);
            availability.setEndDate(end);

            return availabilityRepository.save(availability);
        }
        return null;
    }

    public Boolean deleteAvailability(Integer id) {
        Optional<Availability> result = availabilityRepository.findById(id);
        if(result.isPresent()){
            availabilityRepository.delete(result.get());
            return true;
        }
        return false;
    }

    public List<Availability> getAllByLodgeId(Integer lodgeId) {
        return availabilityRepository.findAllByLodgeId(lodgeId);
    }

    public Availability convertNewAvailabilityDTOToAvailability(NewAvailabilityDTO dto) {
        LocalDate start = LocalDate.parse(dto.getStart());
        LocalDate end = LocalDate.parse(dto.getEnd());
        Availability availability = new Availability();
        Optional<Lodge> optional = lodgeRepository.findById(dto.getLodgeId());
        if(optional.isEmpty()){
            return null;
        }
        availability.setLodge(optional.get());
        availability.setStartDate(start);
        availability.setEndDate(end);
        return availability;
    }

    public AvailabilityDTO convertAvailabilityToAvailabilityDTO(Availability a) {
        AvailabilityDTO dto = new AvailabilityDTO();
        dto.setId(a.getId());
        dto.setStart(a.getStartDate());
        dto.setEnd(a.getEndDate());
        dto.setLodgeId(a.getLodge().getId());

        return dto;
    }
}
