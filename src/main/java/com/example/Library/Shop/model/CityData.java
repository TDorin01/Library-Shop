package com.example.Library.Shop.model;

import lombok.Data;

import java.util.List;

@Data
public class CityData {
    private boolean error;
    private String msg;
    private List<CountryCity> data;
}
