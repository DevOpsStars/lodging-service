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

    public List<Lodge> getAllByHostId(Integer hostId) {
        return lodgeRepository.findAllByHostId(hostId);
    }

    public Optional<Lodge> getById(Integer id) {
        return lodgeRepository.findById(id);
    }

    public Lodge createNew(NewLodgeDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Lodge newLodge = modelMapper.map(dto, Lodge.class);
        return lodgeRepository.save(newLodge);
    }

    public Lodge updateLodge(Lodge updatedLodge) {
        return lodgeRepository.save(updatedLodge);
    }

    public Boolean deleteLodge(Integer id) {
        Optional<Lodge> result = lodgeRepository.findById(id);
        if(result.isPresent()){
            lodgeRepository.delete(result.get());
            return true;
        }
        return false;
    }

    public Lodge convertNewLodgeDTOToLodge(NewLodgeDTO dto) {
        Lodge lodge = new Lodge();
        lodge.setTitle(dto.getTitle());
        lodge.setIsAutoApproved(dto.getIsAutoApproved());
        lodge.setHostId(dto.getHostId());
        lodge.setDescription(dto.getDescription());
        lodge.setBasePrice(dto.getBasePrice());
        lodge.setPriceType(dto.getPriceType());
        lodge.setCountry(dto.getCountry());
        lodge.setCity(dto.getCity());
        lodge.setAddress(dto.getAddress());
        lodge.setMinGuests(dto.getMinGuests());
        lodge.setMaxGuests(dto.getMaxGuests());
        lodge.setHasKitchen(dto.getHasKitchen());
        lodge.setHasWifi(dto.getHasWifi());
        lodge.setHasAC(dto.getHasAC());
        lodge.setHasFreeParking(dto.getHasFreeParking());
        lodge.setHasBalcony(dto.getHasBalcony());

        return lodge;
    }
}
