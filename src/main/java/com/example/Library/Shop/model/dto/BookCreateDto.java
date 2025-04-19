package com.example.Library.Shop.model.dto;
import com.example.Library.Shop.model.Book;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookCreateDto {
    @NotBlank
    private String title;

    @NotBlank
    private String author;

    @PositiveOrZero
    private double price;

    @NotBlank
    private String imageUrl;

    @NotBlank
    private String category;

    public Book toBook() {
        Book book = new Book();
        book.setTitle(this.title);
        book.setAuthor(this.author);
        book.setPrice(this.price);
        book.setImageUrl(this.imageUrl);
        book.setCategory(this.category);
        return book;
    }
    public BookCreateDto(String title, String author, double price, String category, String imageUrl) {
        this.title = title;
        this.author = author;
        this.price = price;
        this.category = category;
        this.imageUrl = imageUrl;
    }
    public BookCreateDto() {
    }

}
