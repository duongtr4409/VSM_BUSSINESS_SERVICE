package com.vsm.business.repository.search;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import com.vsm.business.domain.GoodServiceType;
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
 * Spring Data Elasticsearch repository for the {@link GoodServiceType} entity.
 */
public interface GoodServiceTypeSearchRepository
    extends ElasticsearchRepository<GoodServiceType, Long>, GoodServiceTypeSearchRepositoryInternal {}

interface GoodServiceTypeSearchRepositoryInternal {
    Page<GoodServiceType> search(String query, Pageable pageable);
}

class GoodServiceTypeSearchRepositoryInternalImpl implements GoodServiceTypeSearchRepositoryInternal {

    private final ElasticsearchRestTemplate elasticsearchTemplate;

    GoodServiceTypeSearchRepositoryInternalImpl(ElasticsearchRestTemplate elasticsearchTemplate) {
        this.elasticsearchTemplate = elasticsearchTemplate;
    }

    @Override
    public Page<GoodServiceType> search(String query, Pageable pageable) {
        NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(queryStringQuery(query));
        nativeSearchQuery.setPageable(pageable);
        List<GoodServiceType> hits = elasticsearchTemplate
            .search(nativeSearchQuery, GoodServiceType.class)
            .map(SearchHit::getContent)
            .stream()
            .collect(Collectors.toList());

        return new PageImpl<>(hits, pageable, hits.size());
    }
}
