package com.devops.lodgingservice.controller;

import com.devops.lodgingservice.dto.*;
import com.devops.lodgingservice.model.Lodge;
import com.devops.lodgingservice.model.PriceType;
import com.devops.lodgingservice.service.CalculationService;
import com.devops.lodgingservice.service.LodgeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/lodge")
@Slf4j
public class LodgeController {

    private LodgeService lodgeService;

    private CalculationService calculationService;

    @Autowired
    public LodgeController(LodgeService lodgeService, CalculationService calculationService){
        this.lodgeService = lodgeService;
        this.calculationService = calculationService;
    }

    @PostMapping("/price")
    public ResponseEntity<CalculationResponseDTO> calculatePriceForPeriod(@RequestBody CalculatePriceDTO dto){
        Optional<Lodge> optional = lodgeService.getById(dto.getLodgeId());
        if(optional.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Lodge lodge = optional.get();
        if( lodge.getMinGuests() <= dto.getNumOfGuests()
                && lodge.getMaxGuests() >= dto.getNumOfGuests()){

            Double basePricePerDay = lodge.getPriceType().equals(PriceType.PER_LODGE) ? lodge.getBasePrice()
                    : lodge.getBasePrice() * dto.getNumOfGuests();

            CalculationResponseDTO response = calculationService.calcualtePriceForPeriod(dto, basePricePerDay);
            if(response == null){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/search")
    public ResponseEntity<List<LodgeSearchResponseDTO>> searchLodges(@RequestBody LodgeSearchFilterDTO lodgeSearchFilterDTO) {
        // Pretraziti loze po filteru nekako
        List<LodgeSearchResponseDTO> foundLodges = lodgeService.getFilteredLodges(lodgeSearchFilterDTO);

        return new ResponseEntity<>(foundLodges, HttpStatus.OK);
        // TODO
        // zatim za svaku pronadjenu lozu uraditi poziv calcServisu
    }

    @GetMapping()
    public ResponseEntity<List<LodgeDTO>> getAll() {
        List<Lodge> result = lodgeService.getAll();
        List<LodgeDTO> dtos = result.stream().map(l -> lodgeService.convertLodgeToLodgeDTO(l)).toList();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LodgeDTO> getById(@PathVariable Integer id) {
        Optional<Lodge> result = lodgeService.getById(id);
        return result.map(lodge -> new ResponseEntity<>(lodgeService.convertLodgeToLodgeDTO(result.get()), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/host/{hostId}")
    public ResponseEntity<List<LodgeDTO>> getByHostId(@PathVariable Integer hostId) {
        List<Lodge> result = lodgeService.getAllByHostId(hostId);
        List<LodgeDTO> dtos = result.stream().map(l -> lodgeService.convertLodgeToLodgeDTO(l)).toList();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<LodgeDTO> create(@RequestBody NewLodgeDTO dto){
        Lodge result = lodgeService.createNew(dto);
        if(result != null){
            return new ResponseEntity<>(lodgeService.convertLodgeToLodgeDTO(result), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<LodgeDTO> update(@PathVariable Integer id, @RequestBody NewLodgeDTO dto) {
        Lodge lodge = lodgeService.convertNewLodgeDTOToLodge(dto);
        lodge.setId(id);
        Lodge result = lodgeService.updateLodge(lodge);
        return new ResponseEntity<>(lodgeService.convertLodgeToLodgeDTO(result), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        Boolean result = lodgeService.deleteLodge(id);
        if(result){
            return new ResponseEntity<>("Success", HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
