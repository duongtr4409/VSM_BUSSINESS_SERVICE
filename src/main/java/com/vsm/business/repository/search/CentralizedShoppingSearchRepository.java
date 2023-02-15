package com.vsm.business.repository.search;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import com.vsm.business.domain.CentralizedShopping;
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
 * Spring Data Elasticsearch repository for the {@link CentralizedShopping} entity.
 */
public interface CentralizedShoppingSearchRepository
    extends ElasticsearchRepository<CentralizedShopping, Long>, CentralizedShoppingSearchRepositoryInternal {}

interface CentralizedShoppingSearchRepositoryInternal {
    Page<CentralizedShopping> search(String query, Pageable pageable);
}

class CentralizedShoppingSearchRepositoryInternalImpl implements CentralizedShoppingSearchRepositoryInternal {

    private final ElasticsearchRestTemplate elasticsearchTemplate;

    CentralizedShoppingSearchRepositoryInternalImpl(ElasticsearchRestTemplate elasticsearchTemplate) {
        this.elasticsearchTemplate = elasticsearchTemplate;
    }

    @Override
    public Page<CentralizedShopping> search(String query, Pageable pageable) {
        NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(queryStringQuery(query));
        nativeSearchQuery.setPageable(pageable);
        List<CentralizedShopping> hits = elasticsearchTemplate
            .search(nativeSearchQuery, CentralizedShopping.class)
            .map(SearchHit::getContent)
            .stream()
            .collect(Collectors.toList());

        return new PageImpl<>(hits, pageable, hits.size());
    }
}
