package com.breed.breed.infrastructure.rest_client;

import com.breed.breed.domain.models.BreedDTO;
import com.breed.breed.domain.models.ImageDTO;
import com.breed.breed.domain.InvalidBreedException;
import com.google.gson.Gson;
import com.jayway.restassured.path.json.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@Slf4j
@Component
public class BreedRestClient {

    private static final String URL_LIST = "http://localhost:8081/v1/dogceo/list/all";
    private static final String URL_DETAIL = "http://localhost:8081/v1/dogceo/{breed_name}/images";

    @Autowired
    private RestTemplate restTemplate;

    public String formQueryToSearch(String breedName) {
        return  "message" + "['" + breedName + "']";
    }

    public ArrayList<ImageDTO> formSubBreedImages(ArrayList<String> response) {
        ArrayList<ImageDTO> imagesResponse = new ArrayList<ImageDTO>();
        response.stream().forEach(image -> {
            ImageDTO objectImage = new ImageDTO();
            objectImage.setUrl(image);
            imagesResponse.add(objectImage);
        } );
        return imagesResponse;
    }

    public ArrayList<ImageDTO> getBreedDetail(String breedName) {
        try {
            String restResponse =  restTemplate.getForObject(URL_DETAIL, String.class, breedName);
            ArrayList<String> imagesFromResponse = JsonPath.from(restResponse).get("message[0..1]");
            ArrayList<ImageDTO> response = formSubBreedImages(imagesFromResponse);
            log.info("Detalle obtenido de [{}]", breedName);
            return response;
        } catch (Exception e) {
            log.error("No se pudo obtener el detalle", e);
            throw new InvalidBreedException("No se pudo obtener el detalle");
        }
    }

    public String getSubBreed(String breedName) {
        try {
            String restResponse =  restTemplate.getForObject(URL_LIST, String.class, breedName);
            log.info("Lista obtenida de sub breeds");
            String query = formQueryToSearch(breedName);
            String response = JsonPath.from(restResponse).get(query).toString();
            return response;
        } catch (Exception e) {
            log.error("No se pudo obtener la lista de sub breeds", e);
            throw new InvalidBreedException("No se pudo obtener la lista de sub breeds");
        }
    }

    public String formFullReturn(String breedName) {
        Gson gson = new Gson();
        BreedDTO breedDTO = new BreedDTO();

        String subBreed = getSubBreed(breedName);
        ArrayList<ImageDTO> breedDetail = getBreedDetail(breedName);

        breedDTO.setBreed(breedName);
        breedDTO.setSubBreeds(subBreed);
        breedDTO.setImages(breedDetail);

        String response = gson.toJson(breedDTO);

        return response;
    }

    public String getBreedFull(String breedName) {
        String jsonString = formFullReturn(breedName);
        try {
            return jsonString;
        } catch (Exception e) {
            log.error("No se pudo obtener el detalle", e);
            throw new InvalidBreedException("No se pudo obtener el detalle");
        }
    }

}
