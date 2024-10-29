package com.stocks.stocks_news_search_api.controller;

import com.stocks.stocks_news_search_api.model.News;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/news")
public class NewsController {

    @GetMapping("/search")
    // Take searchTerm in Param
    public Mono<ResponseEntity<List<News>>> search() {
        List<News> newsList = List.of(News.builder().build());
        // Call the service which will call the query and then return response
        return Mono.just(ResponseEntity.of(Optional.of(newsList)));
    }

}
