package com.example.Library.Shop.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookUpdateDto {
    @NotBlank
    private int id;

    @NotBlank
    private String title;

    @NotBlank
    private String author;

    @NotBlank
    @PositiveOrZero
    private double price;

    @NotBlank
    private String imageUrl;

    @NotBlank
    private String category;


}
