package com.example.Library.Shop.service;
import com.example.Library.Shop.model.dto.BookApiDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
public class BookApiService {

    private static final String BOOKS_API_URL = "https://www.googleapis.com/books/v1/volumes?q=intitle:%s&langRestrict=ro&maxResults=1";

    public List<BookApiDto> searchBooks(String title) {
        RestTemplate restTemplate = new RestTemplate();
        String url = String.format(BOOKS_API_URL, title);

        try {
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
            if (response == null || !response.containsKey("items")) {
                return List.of(new BookApiDto("📭 Nicio carte găsită", "", "Pentru titlul: " + title));
            }

            List<Map<String, Object>> items = (List<Map<String, Object>>) response.get("items");

            List<BookApiDto> results = items.stream()
                    .map(item -> (Map<String, Object>) item.get("volumeInfo"))
                    .filter(Objects::nonNull)
                    .map(this::mapToDto)
                    .toList();

            return results.isEmpty()
                    ? List.of(new BookApiDto("📭 Nicio carte găsită", "", "Pentru titlul: " + title))
                    : results;

        } catch (Exception e) {
            log.error("Eroare la interogarea Google Books API: {}", e.getMessage());
            return List.of(new BookApiDto("❌ Eroare API", "", "Nu s-a putut contacta Google Books."));
        }
    }

    private BookApiDto mapToDto(Map<String, Object> volumeInfo) {
        String title = (String) volumeInfo.getOrDefault("title", "Titlu necunoscut");
        List<String> authors = (List<String>) volumeInfo.get("authors");
        String author = (authors != null && !authors.isEmpty()) ? authors.get(0) : "Autor necunoscut";
        String description = (String) volumeInfo.getOrDefault("description", "Fără descriere disponibilă.");
        return new BookApiDto(title, author, description);
    }
}
