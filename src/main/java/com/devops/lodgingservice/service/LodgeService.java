package com.devops.lodgingservice.service;

import com.devops.lodgingservice.dto.NewLodgeDTO;
import com.devops.lodgingservice.model.Lodge;
import com.devops.lodgingservice.repository.LodgeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LodgeService {

    @Autowired
    private LodgeRepository lodgeRepository;

    public List<Lodge> getAll() {
        return lodgeRepository.findAll();
    }

    public Optional<Lodge> getById(Integer id) {
        return lodgeRepository.findById(id);
    }

    public Lodge createNew(NewLodgeDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Lodge newLodge = modelMapper.map(dto, Lodge.class);
        return lodgeRepository.save(newLodge);
    }

    public Lodge updateLodge(Integer id, NewLodgeDTO dto) {
        Optional<Lodge> result = lodgeRepository.findById(id);
        if(result.isPresent()){
            Lodge lodge = result.get();
            ModelMapper modelMapper = new ModelMapper();
            modelMapper.map(dto, lodge);
            lodgeRepository.save(lodge);
        }
        return null;
    }

    public Boolean deleteLodge(Integer id) {
        Optional<Lodge> result = lodgeRepository.findById(id);
        if(result.isPresent()){
            lodgeRepository.delete(result.get());
            return true;
        }
        return false;
    }



}
