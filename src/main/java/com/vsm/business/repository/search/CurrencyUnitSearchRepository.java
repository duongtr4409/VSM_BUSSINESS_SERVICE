package com.vsm.business.repository.search;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import com.vsm.business.domain.CurrencyUnit;
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
 * Spring Data Elasticsearch repository for the {@link CurrencyUnit} entity.
 */
public interface CurrencyUnitSearchRepository extends ElasticsearchRepository<CurrencyUnit, Long>, CurrencyUnitSearchRepositoryInternal {}

interface CurrencyUnitSearchRepositoryInternal {
    Page<CurrencyUnit> search(String query, Pageable pageable);
}

class CurrencyUnitSearchRepositoryInternalImpl implements CurrencyUnitSearchRepositoryInternal {

    private final ElasticsearchRestTemplate elasticsearchTemplate;

    CurrencyUnitSearchRepositoryInternalImpl(ElasticsearchRestTemplate elasticsearchTemplate) {
        this.elasticsearchTemplate = elasticsearchTemplate;
    }

    @Override
    public Page<CurrencyUnit> search(String query, Pageable pageable) {
        NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(queryStringQuery(query));
        nativeSearchQuery.setPageable(pageable);
        List<CurrencyUnit> hits = elasticsearchTemplate
            .search(nativeSearchQuery, CurrencyUnit.class)
            .map(SearchHit::getContent)
            .stream()
            .collect(Collectors.toList());

        return new PageImpl<>(hits, pageable, hits.size());
    }
}
