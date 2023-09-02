package com.devops.lodgingservice.controller;

import com.devops.lodgingservice.dto.NewPriceModificationDTO;
import com.devops.lodgingservice.dto.PriceModificationDTO;
import com.devops.lodgingservice.model.PriceModification;
import com.devops.lodgingservice.service.PriceModificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/price")
@Slf4j
public class PriceModificationController {

    private PriceModificationService priceService;

    @Autowired
    public PriceModificationController(PriceModificationService priceService) {
        this.priceService = priceService;
    }

    @GetMapping()
    public ResponseEntity<List<PriceModificationDTO>> getAll(){
        List<PriceModification> result = priceService.getAll();
        List<PriceModificationDTO> dtos = result.stream().map(p -> priceService.priceModificationToPriceModificationDTO(p)).toList();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping("/lodge/{id}")
    public ResponseEntity<List<PriceModificationDTO>> getAllByLodgeId(@PathVariable Integer id) {
        List<PriceModification> result = priceService.getAllByLodgeId(id);
        List<PriceModificationDTO> dtos = result.stream().map(p -> priceService.priceModificationToPriceModificationDTO(p)).toList();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PriceModificationDTO> getById(@PathVariable Integer id) {
        Optional<PriceModification> result = priceService.getById(id);
        return result.map(p -> new ResponseEntity<>(priceService.priceModificationToPriceModificationDTO(result.get()),
                HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping()
    public ResponseEntity<PriceModificationDTO> create(@RequestBody NewPriceModificationDTO newDto){
        PriceModification result = priceService.createNew(newDto);
        if(result != null){
            PriceModificationDTO dto = priceService.priceModificationToPriceModificationDTO(result);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<PriceModificationDTO> update(@PathVariable Integer id, @RequestBody NewPriceModificationDTO dto) {
        PriceModification result = priceService.updatePriceModification(id, dto);
        if(result != null){
            return new ResponseEntity<>(priceService.priceModificationToPriceModificationDTO(result), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        Boolean result = priceService.deletePriceModification(id);
        if(result){
            return new ResponseEntity<>("Success", HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
