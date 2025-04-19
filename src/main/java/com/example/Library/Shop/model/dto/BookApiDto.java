package com.example.Library.Shop.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookApiDto {
    private String title;
    private String author;
    private String description;
}
