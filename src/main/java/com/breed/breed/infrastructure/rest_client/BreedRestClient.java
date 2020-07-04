package com.breed.breed.infrastructure.rest_client;

import com.breed.breed.domain.Image;
import com.breed.breed.domain.InvalidBreedException;
import com.google.gson.Gson;
import com.jayway.restassured.path.json.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;

@Slf4j
@Component
public class BreedRestClient {

    private static final String URL_LIST = "https://dog.ceo/api/breeds/list/all";
    private static final String URL_DETAIL = "https://dog.ceo/api/breed/{breed_name}/images";

    @Autowired
    private RestTemplate restTemplate;

    public String formQueryToSearch(String breedName) {
        return  "message" + "['" + breedName + "']";
    }

    public ArrayList<Image> formSubBreedImages(ArrayList<String> response) {
        ArrayList<Image> imagesResponse = new ArrayList<Image>();
        response.stream().forEach(image -> {
            Image objectImage = new Image();
            objectImage.setUrl(image);
            imagesResponse.add(objectImage);
        } );
        return imagesResponse;
    }

    public ArrayList<Image> getBreedDetail(String breedName) {
        try {
            String restResponse =  restTemplate.getForObject(URL_DETAIL, String.class, breedName);
            ArrayList<String> imagesFromResponse = JsonPath.from(restResponse).get("message[0..1]");
            ArrayList<Image> response = formSubBreedImages(imagesFromResponse);
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
        HashMap<String, Object> total = new HashMap<String, Object>();

        String subBreed = getSubBreed(breedName);
        ArrayList<Image> breedDetail = getBreedDetail(breedName);

        total.put("breed:", breedName);
        total.put("images", breedDetail);
        total.put("subBreeds", subBreed);
        String response = gson.toJson(total);

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
