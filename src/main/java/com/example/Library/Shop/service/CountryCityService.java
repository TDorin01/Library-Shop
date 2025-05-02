package com.example.Library.Shop.service;
import com.example.Library.Shop.model.CityData;
import com.example.Library.Shop.model.CountryCity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class CountryCityService {

    private static final String COUNTRY_CITY_API_URL = "https://countriesnow.space/api/v0.1/countries";

    private final RestTemplate restTemplate = new RestTemplate();

    public List<CountryCity> getAllCountriesAndCities() {
        try {
            ResponseEntity<CityData> response = restTemplate.getForEntity(COUNTRY_CITY_API_URL, CityData.class);
            CityData body = response.getBody();

            if (body != null && !body.isError()) {
                return body.getData();
            } else {
                log.warn("Empty or error response from country-city API.");
                return Collections.emptyList();
            }

        } catch (Exception e) {
            log.error("Error fetching countries and cities", e);
            return Collections.emptyList();
        }
    }
}