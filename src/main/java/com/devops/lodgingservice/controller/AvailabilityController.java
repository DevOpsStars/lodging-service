package com.devops.lodgingservice.controller;

import com.devops.lodgingservice.dto.AvailabilityDTO;
import com.devops.lodgingservice.dto.NewAvailabilityDTO;
import com.devops.lodgingservice.model.Availability;
import com.devops.lodgingservice.service.AvailabilityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/availability")
@Slf4j
@CrossOrigin("*")
public class AvailabilityController {

    private AvailabilityService availabilityService;

    @Autowired
    public AvailabilityController(AvailabilityService availabilityService){
        this.availabilityService = availabilityService;
    }

    @GetMapping()
    public ResponseEntity<List<AvailabilityDTO>> getAll() {
        List<Availability> result = availabilityService.getAll();
        List<AvailabilityDTO> dtos = result.stream().map(a -> availabilityService.availabilityToAvailabilityDTO(a)).toList();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping("/lodge/{id}")
    public ResponseEntity<List<AvailabilityDTO>> getAllByLodgeId(@PathVariable Integer id) {
        List<Availability> result = availabilityService.getAllByLodgeId(id);
        List<AvailabilityDTO> dtos = result.stream().map(a -> availabilityService.availabilityToAvailabilityDTO(a)).toList();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AvailabilityDTO> getById(@PathVariable Integer id) {
        Optional<Availability> result = availabilityService.getById(id);
        return result.map(lodge -> new ResponseEntity<>(availabilityService.availabilityToAvailabilityDTO(result.get()),
                HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping()
    public ResponseEntity<AvailabilityDTO> create(@RequestBody NewAvailabilityDTO newDto){
        Availability result = availabilityService.createNew(newDto);
        if(result != null){
            AvailabilityDTO dto = availabilityService.availabilityToAvailabilityDTO(result);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<AvailabilityDTO> update(@PathVariable Integer id, @RequestBody NewAvailabilityDTO dto) {
        Availability result = availabilityService.updateAvailability(id, dto);
        if(result != null){
            return new ResponseEntity<>(availabilityService.availabilityToAvailabilityDTO(result), HttpStatus.OK);
        }
       return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        Boolean result = availabilityService.deleteAvailability(id);
        if(result){
            return new ResponseEntity<>("Success", HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
