package com.devops.lodgingservice.services;

import com.devops.lodgingservice.model.Lodge;
import com.devops.lodgingservice.repository.LodgeRepository;
import com.devops.lodgingservice.service.LodgeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class LodgeServiceUnitTest {

    @Mock
    LodgeRepository lodgeRepository;

    @InjectMocks
    LodgeService lodgeService;

    @Test
    public void givenExistingLodges_whenGettingAll_thenReturnsAllLodges() {
        List<Lodge> allLodges = new ArrayList<>();
        allLodges.add(new Lodge());
        allLodges.add(new Lodge());
        when(lodgeRepository.findAll()).thenReturn(allLodges);

        List<Lodge> result = lodgeService.getAll();

        assertEquals(allLodges.size(), result.size());
    }

    @Test
    void givenLodgeId_whenDeletingLodge_thenLodgeHasBeenDeleted() {
        Lodge existing = new Lodge();
        existing.setId(1);
        when(lodgeRepository.findById(1)).thenReturn(Optional.of(existing));

        Boolean result = lodgeService.deleteLodge(1);

        assertTrue(result);
    }

    @Test
    void givenInvalidLodgeId_whenDeletingNonExistentLodge_thenLodgeHasNotBeenDeleted() {
        when(lodgeRepository.findById(1)).thenReturn(Optional.empty());

        Boolean result = lodgeService.deleteLodge(1);

        assertFalse(result);
    }

}