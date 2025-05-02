package com.example.Library.Shop.controller;

import com.example.Library.Shop.model.CountryCity;
import com.example.Library.Shop.service.CountryCityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/locations")
public class CountryCityController {

    private final CountryCityService countryCityService;

    @GetMapping
    public List<CountryCity> getAllLocations() {
        return countryCityService.getAllCountriesAndCities();
    }
}
