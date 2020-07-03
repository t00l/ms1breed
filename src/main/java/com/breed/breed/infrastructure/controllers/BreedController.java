package com.breed.breed.infrastructure.controllers;

import com.breed.breed.domain.service.BreedService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/")
@AllArgsConstructor
public class BreedController {

    private BreedService breedService;

    @GetMapping(value = "/list/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getBreedList() {
        return breedService.getBreedList();
    }

    @GetMapping(value = "/{breed_name}/images", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getBreedDetail(@PathVariable("breed_name") String breed_name) {
        return breedService.getBreedDetail(breed_name);
    }
}
