package com.example.Library.Shop.model;

import lombok.Data;

import java.util.List;

@Data
public class CountryCity {

    private String country;
    private List<String> cities;
}
