package com.stocks.stocks_news_search_api.component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stocks.stocks_news_search_api.model.News;
import com.stocks.stocks_news_search_api.repository.NewsRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Component;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class DataLoader {

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    private static final String INDEX_NAME = "news";

    @PostConstruct
    public void loadDataIntoElasticSearch() {
        try {
            createIndex();
            List<News> news = loadNewsFromJson();
            newsRepository.saveAll(news).collectList().block();
            System.out.println("Data successfully loaded into Elasticsearch");
        } catch (Exception e) {
            System.err.println("Error loading data into Elasticsearch: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void createIndex() {
        try {
            String mappingJson = new String(
                    new ClassPathResource("elasticsearch-mapping.json").getInputStream().readAllBytes(),
                    StandardCharsets.UTF_8);
            if (!elasticsearchOperations.indexOps(IndexCoordinates.of(INDEX_NAME)).exists()) {
                elasticsearchOperations.indexOps(IndexCoordinates.of(INDEX_NAME)).create();
                Document mapping = Document.parse(mappingJson);
                elasticsearchOperations.indexOps(IndexCoordinates.of(INDEX_NAME)).putMapping(mapping);
                System.out.println("Index created with mapping");
            } else {
                System.out.println("Index already exists");
            }
        } catch (IOException e) {
            System.err.println("Error creating index: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Unexpected error creating index: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private List<News> loadNewsFromJson() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            // Debugging statement to check if the file is being accessed
            System.out.println("Attempting to load data from JSON file...");

            // Load the news data from the JSON file
            List<News> newsList = mapper.readValue(
                    new ClassPathResource("data.json").getInputStream(),
                    new TypeReference<List<News>>() {
                    });

            // Debugging statement to confirm successful loading
            System.out.println("Successfully loaded data from JSON file. Number of records: " + newsList.size());

            return newsList;
        } catch (IOException e) {
            System.err.println("Error loading data from JSON: " + e.getMessage());
            e.printStackTrace();
            return List.of();
        }
    }

}
