package com.devops.lodgingservice.service;

import com.devops.lodgingservice.dto.LodgeDTO;
import com.devops.lodgingservice.dto.LodgeSearchFilterDTO;
import com.devops.lodgingservice.dto.LodgeSearchResponseDTO;
import com.devops.lodgingservice.dto.NewLodgeDTO;
import com.devops.lodgingservice.model.Lodge;
import com.devops.lodgingservice.repository.LodgeRepository;
import com.devops.lodgingservice.repository.specifications.LodgeSpecification;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LodgeService {

    @Autowired
    private LodgeRepository lodgeRepository;

    @Autowired
    private AvailabilityService availabilityService;

    @Autowired
    private PriceModificationService priceModificationService;

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
        Lodge newLodge = convertNewLodgeDTOToLodge(dto);
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
        lodge.setAvailabilities(new ArrayList<>());
        lodge.setPriceModifications(new ArrayList<>());
        lodge.setPhotos(new ArrayList<>());
        return lodge;
    }

    public LodgeDTO convertLodgeToLodgeDTO(Lodge lodge) {
        LodgeDTO dto = new LodgeDTO();
        dto.setId(lodge.getId());
        dto.setTitle(lodge.getTitle());
        dto.setIsAutoApproved(lodge.getIsAutoApproved());
        dto.setHostId(lodge.getHostId());
        dto.setDescription(lodge.getDescription());
        dto.setBasePrice(lodge.getBasePrice());
        dto.setPriceType(lodge.getPriceType());
        dto.setCountry(lodge.getCountry());
        dto.setCity(lodge.getCity());
        dto.setAddress(lodge.getAddress());
        dto.setMinGuests(lodge.getMinGuests());
        dto.setMaxGuests(lodge.getMaxGuests());
        dto.setHasKitchen(lodge.getHasKitchen());
        dto.setHasWifi(lodge.getHasWifi());
        dto.setHasAC(lodge.getHasAC());
        dto.setHasFreeParking(lodge.getHasFreeParking());
        dto.setHasBalcony(lodge.getHasBalcony());
        if(lodge.getAvailabilities() != null){
            dto.setAvailabilities(lodge.getAvailabilities().stream()
                    .map(a -> availabilityService.availabilityToAvailabilityDTO(a)).toList());
        }
        if(lodge.getPriceModifications() != null){
            dto.setPriceModifications(lodge.getPriceModifications().stream()
                    .map(p -> priceModificationService.priceModificationToPriceModificationDTO(p)).toList());
        }
        return dto;
    }
    public List<LodgeSearchResponseDTO> getFilteredLodges(LodgeSearchFilterDTO lodgeSearchFilterDTO) {
        Pageable pageable = PageRequest.of(lodgeSearchFilterDTO.getPage(), lodgeSearchFilterDTO.getPageSize());

        Page<Lodge> responses = lodgeRepository.findAll(LodgeSpecification.getFilteredLodges(lodgeSearchFilterDTO), pageable);

        Page<LodgeSearchResponseDTO> responsesDto = responses.map(lodge ->
                new LodgeSearchResponseDTO(lodge.getId(),
                        lodge.getTitle(),
                        lodge.getDescription(),
                        lodge.getBasePrice(),
                        lodge.getPriceType(),
                        lodge.getCountry(),
                        lodge.getCity(),
                        lodge.getAddress(),
                        lodge.getMinGuests(),
                        lodge.getMaxGuests(),
                        null));
        return responsesDto.getContent();


    }
//    public List<HistoryResponseDto> getFilteredHistory(HistoryFilterDto historyFilterDto) {
//        Pageable pageable = PageRequest.of(historyFilterDto.getPage(), historyFilterDto.getPageSize());
//        Page<PaymentResponse> responses = paymentResponseRepository.findAll(PaymentResponseSpecification.getFilteredResponses(historyFilterDto), pageable);
//        Page<HistoryResponseDto> responsesDto = responses.map(paymentResponse -> new HistoryResponseDto(historyFilterDto.getServiceName(), paymentResponse.getTransactionStatus().name(), paymentResponse.getPaymentRequest().getAmount(), paymentResponse.getPaymentRequest().getMerchantTimestamp()));
//        return responsesDto.getContent();
//    }
}






























