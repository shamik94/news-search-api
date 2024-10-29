package com.stocks.stocks_news_search_api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(indexName = "news")
@JsonIgnoreProperties(ignoreUnknown = true)
public class News {

    @Id
    Long id;

    String headline;
    String image;
    String related;
    String source;
    String summary;
    String url;
    String category;

    // TODO Add DateTime
}
