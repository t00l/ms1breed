package com.breed.breed.infrastructure.rest_client;

import com.breed.breed.domain.InvalidBreedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class BreedRestClient {

    String urlList = "https://dog.ceo/api/breeds/list/all";
    String urlDetail = "https://dog.ceo/api/breed/{breed_name}/images/";

    @Autowired
    private RestTemplate restTemplate;

    public String getBreedList() {
        try {
            String response =  restTemplate.getForObject(urlList, String.class);
            log.info("Lista obtenida de breeds");
            return  response;
        } catch (Exception e) {
            log.error("No se pudo obtener la lista", e);
            throw new InvalidBreedException("No se pudo obtener la lista");
        }
    }

    public String getBreedDetail(String breed_name) {
        try {
            String response =  restTemplate.getForObject(urlDetail, String.class, breed_name);
            log.info("Detalle obtenido de [{}]", breed_name);
            return  response;
        } catch (Exception e) {
            log.error("No se pudo obtener el detalle", e);
            throw new InvalidBreedException("No se pudo obtener el detalle");
        }
    }
}
