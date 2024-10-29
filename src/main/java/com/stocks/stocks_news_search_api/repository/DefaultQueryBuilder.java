package com.stocks.stocks_news_search_api.repository;

import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Operator;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.stereotype.Component;

@Component
public class DefaultQueryBuilder implements QueryBuilder {

    @Override
    public NativeQuery getQuery(final String query) {
        // Build the MatchQuery to search in the "headline" field
        Query matchQuery = MatchQuery.of(mq -> mq
                .field("headline")      // Field to search in
                .query(query)           // Search query
                .operator(Operator.And)
        )._toQuery();

        //TODO search in  related, source, summary
        // Wrap the query into NativeQuery
        return NativeQuery.builder()
                .withQuery(matchQuery)   // Apply the match query
                .build();
    }
}
