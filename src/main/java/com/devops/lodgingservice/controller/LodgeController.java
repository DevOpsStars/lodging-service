package com.devops.lodgingservice.controller;

import com.devops.lodgingservice.dto.NewLodgeDTO;
import com.devops.lodgingservice.model.Lodge;
import com.devops.lodgingservice.service.LodgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/lodge")
public class LodgeController {

    @Autowired
    private LodgeService lodgeService;

    @GetMapping()
    public ResponseEntity<List<Lodge>> getAll() {
        List<Lodge> result = lodgeService.getAll();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Lodge> getById(@PathVariable Integer id) {
        Optional<Lodge> result = lodgeService.getById(id);
        return result.map(lodge -> new ResponseEntity<>(lodge, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping()
    public ResponseEntity<Lodge> create(@RequestBody NewLodgeDTO dto){
        Lodge result = lodgeService.createNew(dto);
        if(result != null){
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Lodge> update(@PathVariable Integer id, @RequestBody NewLodgeDTO dto) {
        Lodge result = lodgeService.updateLodge(id, dto);
        if(result != null){
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
