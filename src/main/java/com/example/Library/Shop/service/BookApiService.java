package com.example.Library.Shop.service;

import com.example.Library.Shop.model.dto.BookApiDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class BookApiService {
    private final RestTemplate restTemplate = new RestTemplate();

    public List<BookApiDto> searchRomanianBooksByTitle(String title) {
        String url = "https://www.googleapis.com/books/v1/volumes?q=intitle:" + title + "&langRestrict=ro&maxResults=5";

        try {
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);

            if (response == null || !response.containsKey("items")) {
                return List.of(new BookApiDto("📭 Nicio carte găsită", "", "Pentru titlul: " + title));
            }

            List<Map<String, Object>> items = (List<Map<String, Object>>) response.get("items");
            List<BookApiDto> results = new ArrayList<>();

            for (Map<String, Object> item : items) {
                Map<String, Object> volumeInfo = (Map<String, Object>) item.get("volumeInfo");
                if (volumeInfo == null) continue;

                String bookTitle = (String) volumeInfo.getOrDefault("title", "Titlu necunoscut");
                List<String> authors = (List<String>) volumeInfo.get("authors");
                String author = (authors != null && !authors.isEmpty()) ? authors.get(0) : "Autor necunoscut";
                String description = (String) volumeInfo.getOrDefault("description", "Fără descriere disponibilă.");

                results.add(new BookApiDto(bookTitle, author, description));
            }

            return results.isEmpty()
                    ? List.of(new BookApiDto("📭 Nicio carte găsită", "", ""))
                    : results;

        } catch (Exception e) {
            System.err.println("Eroare API: " + e.getMessage());
            return List.of(new BookApiDto("❌ Eroare API", "", "Nu s-a putut contacta Google Books."));
        }
    }
}
