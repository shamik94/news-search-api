package com.stocks.stocks_news_search_api.repository;

import com.stocks.stocks_news_search_api.model.News;
import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends ReactiveElasticsearchRepository<News, Long> {
}
