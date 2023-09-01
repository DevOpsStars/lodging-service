package com.devops.lodgingservice.services;

import com.devops.lodgingservice.dto.NewAvailabilityDTO;
import com.devops.lodgingservice.model.Availability;
import com.devops.lodgingservice.model.Lodge;
import com.devops.lodgingservice.repository.AvailabilityRepository;
import com.devops.lodgingservice.repository.LodgeRepository;
import com.devops.lodgingservice.service.AvailabilityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AvailabilityServiceUnitTest {

    @Mock
    AvailabilityRepository availabilityRepository;

    @Mock
    LodgeRepository lodgeRepository;

    @InjectMocks
    AvailabilityService availabilityService;

    //update
    @Test
    public void givenNewAvailability_whenCreating_isCreatedSuccessfully(){
        Lodge lodge =  new Lodge();
        lodge.setId(1);
        Availability a = new Availability(1, lodge, LocalDate.parse("2023-04-04"), LocalDate.parse("2023-04-20"));
        List<Availability> availabilities = new ArrayList<>();
        availabilities.add(a);

        NewAvailabilityDTO dto = new NewAvailabilityDTO(1,"2023-05-05","2023-05-15");

        when(lodgeRepository.existsById(1)).thenReturn(true);
        when(availabilityRepository.findAllByLodgeId(1)).thenReturn(availabilities);
        when(lodgeRepository.findById(1)).thenReturn(Optional.of(lodge));
        when(availabilityRepository.save(any())).thenReturn(new Availability());

        Availability created = availabilityService.createNew(dto);

        assertNotNull(created);
    }

    @Test
    public void givenNewAvailabilityWhichOverlapsWithExistent_whenCreating_isNotSuccessfull(){
        Lodge lodge =  new Lodge();
        lodge.setId(1);
        Availability a = new Availability(1, lodge, LocalDate.parse("2023-04-04"), LocalDate.parse("2023-10-04"));
        List<Availability> availabilities = new ArrayList<>();
        availabilities.add(a);

        NewAvailabilityDTO dto = new NewAvailabilityDTO(1,"2023-10-02","2023-12-15");

        when(lodgeRepository.existsById(1)).thenReturn(true);
        when(availabilityRepository.findAllByLodgeId(1)).thenReturn(availabilities);

        Availability failed = availabilityService.createNew(dto);

        assertNull(failed);
    }


    @Test
    public void givenValidUpdateRequest_whenUpdating_isSuccessfull(){
        Lodge lodge =  new Lodge();
        lodge.setId(1);
        Availability a1 = new Availability(1, lodge, LocalDate.parse("2023-04-01"), LocalDate.parse("2023-05-01"));
        Availability a2 = new Availability(2, lodge, LocalDate.parse("2023-07-04"), LocalDate.parse("2023-09-04"));
        List<Availability> availabilities = new ArrayList<>();
        availabilities.add(a1);
        availabilities.add(a2);

        NewAvailabilityDTO dto = new NewAvailabilityDTO(1,"2023-04-02","2023-05-02");

        when(availabilityRepository.existsById(1)).thenReturn(true);
        when(lodgeRepository.existsById(1)).thenReturn(true);
        when(availabilityRepository.findAllByLodgeId(1)).thenReturn(availabilities);
        when(availabilityRepository.findById(1)).thenReturn(Optional.of(a1));
        when(availabilityRepository.save(any())).thenReturn(new Availability());

        Availability updated = availabilityService.updateAvailability(1, dto);

        assertNotNull(updated);
    }

    @Test
    public void givenOverlappingUpdateRequest_whenUpdating_operationIsForbidden(){
        Lodge lodge =  new Lodge();
        lodge.setId(1);
        Availability a1 = new Availability(1, lodge, LocalDate.parse("2023-04-01"), LocalDate.parse("2023-05-01"));
        Availability a2 = new Availability(2, lodge, LocalDate.parse("2023-05-04"), LocalDate.parse("2023-09-04"));
        List<Availability> availabilities = new ArrayList<>();
        availabilities.add(a1);
        availabilities.add(a2);

        NewAvailabilityDTO dto = new NewAvailabilityDTO(1,"2023-04-02","2023-05-05");

        when(availabilityRepository.existsById(1)).thenReturn(true);
        when(lodgeRepository.existsById(1)).thenReturn(true);
        when(availabilityRepository.findAllByLodgeId(1)).thenReturn(availabilities);

        Availability failed = availabilityService.updateAvailability(1, dto);

        assertNull(failed);
    }
}
