package com.vsm.business.repository.search;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import com.vsm.business.domain.InformationInExchange;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link InformationInExchange} entity.
 */
public interface InformationInExchangeSearchRepository
    extends ElasticsearchRepository<InformationInExchange, Long>, InformationInExchangeSearchRepositoryInternal {}

interface InformationInExchangeSearchRepositoryInternal {
    Page<InformationInExchange> search(String query, Pageable pageable);
}

class InformationInExchangeSearchRepositoryInternalImpl implements InformationInExchangeSearchRepositoryInternal {

    private final ElasticsearchRestTemplate elasticsearchTemplate;

    InformationInExchangeSearchRepositoryInternalImpl(ElasticsearchRestTemplate elasticsearchTemplate) {
        this.elasticsearchTemplate = elasticsearchTemplate;
    }

    @Override
    public Page<InformationInExchange> search(String query, Pageable pageable) {
        NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(queryStringQuery(query));
        nativeSearchQuery.setPageable(pageable);
        List<InformationInExchange> hits = elasticsearchTemplate
            .search(nativeSearchQuery, InformationInExchange.class)
            .map(SearchHit::getContent)
            .stream()
            .collect(Collectors.toList());

        return new PageImpl<>(hits, pageable, hits.size());
    }
}
