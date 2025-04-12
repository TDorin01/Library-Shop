package com.example.Library.Shop.model.dto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class BookCreateDto {
    @NotNull
    private String title;
    @NotNull
    private String author;
    @PositiveOrZero
    private double price;
    @NotNull
    private String imageUrl;
    @NotNull
    private String category;

}
