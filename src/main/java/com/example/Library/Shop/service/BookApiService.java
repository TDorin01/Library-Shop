package com.example.Library.Shop.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class BookApiService {
    private final RestTemplate restTemplate = new RestTemplate();

    public List<String> searchRomanianBooksByTitle(String title) {
        String url = "https://www.googleapis.com/books/v1/volumes?q=intitle:" + title + "&langRestrict=ro&maxResults=5";

        try {
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);

            if (response == null || !response.containsKey("items")) {
                return List.of("📭 Nicio carte găsită pentru titlul \"" + title + "\".");
            }

            List<Map<String, Object>> items = (List<Map<String, Object>>) response.get("items");
            if (items == null || items.isEmpty()) {
                return List.of("📭 Nicio carte românească găsită pentru \"" + title + "\".");
            }

            Map<String, Object> firstItem = items.get(0);
            Map<String, Object> volumeInfo = (Map<String, Object>) firstItem.get("volumeInfo");

            if (volumeInfo == null) {
                return List.of("⚠️ Nu s-au găsit informații detaliate pentru prima carte.");
            }

            String bookTitle = (String) volumeInfo.getOrDefault("title", "Titlu necunoscut");
            List<String> authors = (List<String>) volumeInfo.get("authors");
            String author = (authors != null && !authors.isEmpty()) ? authors.get(0) : "Autor necunoscut";
            String description = (String) volumeInfo.getOrDefault("description", "Fără descriere disponibilă.");

            List<String> results = new ArrayList<>();
            results.add("📖 Titlu: " + bookTitle);
            results.add("✍️ Autor: " + author);
            results.add("📝 Rezumat: " + description);

            return results;

        } catch (Exception e) {
            System.err.println("Eroare API: " + e.getMessage());
            return List.of("❌ Eroare la conectarea cu Google Books API.");
        }
    }
}
