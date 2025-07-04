package com.example.Library.Shop.controller;

import com.example.Library.Shop.model.dto.BookApiDto;
import com.example.Library.Shop.service.BookApiService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
public class BookApiController {

    private final BookApiService bookApiService;

    @Operation(summary = "Cauta cartile")
    @GetMapping("/search")
    public List<BookApiDto> searchBooks(@RequestParam String title) {
        return bookApiService.searchBooks(title);
    }
}

