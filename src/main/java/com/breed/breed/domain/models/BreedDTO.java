package com.breed.breed.domain.models;

import lombok.Data;

import java.util.ArrayList;

@Data
public class BreedDTO {
    String breed;
    String subBreeds;
    ArrayList images;
}
