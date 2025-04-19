package com.example.Library.Shop.model;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name="books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    private int id;
    private String title;
    private String author;
    private double price;
    private String imageUrl;
    private String category;
    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;


}
