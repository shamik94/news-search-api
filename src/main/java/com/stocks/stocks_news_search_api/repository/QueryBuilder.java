package com.stocks.stocks_news_search_api.repository;

import org.springframework.data.elasticsearch.client.elc.NativeQuery;

public interface QueryBuilder {

    NativeQuery getQuery(String query);
}
