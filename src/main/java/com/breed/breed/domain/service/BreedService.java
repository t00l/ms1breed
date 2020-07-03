package com.breed.breed.domain.service;

import com.breed.breed.infrastructure.rest_client.BreedRestClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BreedService {

    @Autowired
    private BreedRestClient breedRestClient;

    public String getBreedList() {
        log.info("entramos a la lista de breeds");
        return breedRestClient.getBreedList();
    }

    public String getBreedDetail(String breed_name) {
        log.info("entramos al detalle de [{}]", breed_name);
        return breedRestClient.getBreedDetail(breed_name);
    }
}

