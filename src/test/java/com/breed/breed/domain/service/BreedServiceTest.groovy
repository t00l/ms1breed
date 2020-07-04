package com.breed.breed.domain.service

import com.breed.breed.domain.InvalidBreedException
import com.breed.breed.infrastructure.rest_client.BreedRestClient
import spock.lang.Specification

class BreedServiceTest extends Specification {

    BreedRestClient breedRestClient = Mock(BreedRestClient)

    BreedService breedService = new BreedService()

    void setup() {
        breedService.breedRestClient = breedRestClient
    }

    void "return a list of breeds if response is ok"() {
        given: "happy path"
        breedRestClient.getBreedList() >> { return responseString }
        when: "try to retrieve a list"
        String response = breedService.getBreedList()
        then:
        noExceptionThrown()
        response.equals(responseString)
    }

    void "return a exception when the endpoint try to get a list"() {
        given:
        breedRestClient.getBreedList() >> { throw new InvalidBreedException("No se pudo obtener la lista"
        ) }
        when: "try to retrieve a list"
        String response = breedService.getBreedList()
        then:
        def error = thrown(InvalidBreedException)
        error.getMessage() == "No se pudo obtener la lista"
    }

    void "return a detail of breeds if response is ok"() {
        given: "happy path"
        String breed = "boxer"
        breedRestClient.getBreedDetail(_) >> { return responseDetailString }
        when: "try to retrieve a detail"
        String response = breedService.getBreedDetail(breed)
        then:
        noExceptionThrown()
        response.equals(responseDetailString)
    }

    void "return a exception when the endpoint try to get a detail of breed"() {
        given:
        String breed = "boxer"
        breedRestClient.getBreedDetail(_) >> { throw new InvalidBreedException("No se pudo obtener el detalle"
        ) }
        when: "try to retrieve a detail"
        String response = breedService.getBreedDetail(breed)
        then:
        def error = thrown(InvalidBreedException)
        error.getMessage() == "No se pudo obtener el detalle"
    }

    void "return full path is response is ok"() {
        given: "happy path"
        String breed = "boxer"
        breedRestClient.getBreedFull(_) >> { return fullResponse }
        when: "try to retrieve a full response"
        String response = breedService.getBreedFull(breed)
        then:
        noExceptionThrown()
        response.equals(fullResponse)
    }

    void "return a exception when the endpoint try to get a full response"() {
        given:
        String breed = "boxer"
        breedRestClient.getBreedFull(_) >> { throw new InvalidBreedException("No se pudo obtener el detalle"
        ) }
        when: "try to retrieve a detail"
        String response = breedService.getBreedFull(breed)
        then:
        def error = thrown(InvalidBreedException)
        error.getMessage() == "No se pudo obtener el detalle"
    }

    private String responseString = "{\n" +
            "    \"message\": [\n" +
            "        \"https://images.dog.ceo/breeds/bulldog-boston/n02096585_10380.jpg\",\n" +
            "        \"https://images.dog.ceo/breeds/bulldog-boston/n02096585_10452.jpg\",\n" +
            "        \"https://images.dog.ceo/breeds/bulldog-boston/n02096585_10596.jpg\",\n" +
            "        \"https://images.dog.ceo/breeds/bulldog-french/n02108915_9804.jpg\",\n" +
            "        \"https://images.dog.ceo/breeds/bulldog-french/n02108915_9899.jpg\"\n" +
            "    ],\n" +
            "    \"status\": \"success\"\n" +
            "}";

    private String responseDetailString = "{\n" +
            "    \"message\": [\n" +
            "        \"https://images.dog.ceo/breeds/boxer/IMG_0002.jpg\",\n" +
            "        \"https://images.dog.ceo/breeds/boxer/IMG_3394.jpg\",\n" +
            "        \"https://images.dog.ceo/breeds/boxer/n02108089_1.jpg\",\n" +
            "        \"https://images.dog.ceo/breeds/boxer/n02108089_1003.jpg\",\n" +
            "        \"https://images.dog.ceo/breeds/boxer/n02108089_10229.jpg\"\n" +
            "    ],\n" +
            "    \"status\": \"success\"\n" +
            "}";

    private String fullResponse = "{\n" +
            "    \"subBreeds\": \"[boston, english, french]\",\n" +
            "    \"images\": [\n" +
            "        {\n" +
            "            \"url\": \"https://images.dog.ceo/breeds/bulldog-boston/n02096585_10380.jpg\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"url\": \"https://images.dog.ceo/breeds/bulldog-boston/n02096585_10452.jpg\"\n" +
            "        }\n" +
            "    ],\n" +
            "    \"breed:\": \"bulldog\"\n" +
            "}";
}