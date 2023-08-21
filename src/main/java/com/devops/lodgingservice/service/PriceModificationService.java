package com.devops.lodgingservice.service;

import com.devops.lodgingservice.repository.PriceModificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PriceModificationService {

    @Autowired
    private PriceModificationRepository priceModificationRepository;
}
