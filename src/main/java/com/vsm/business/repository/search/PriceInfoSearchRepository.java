package com.vsm.business.repository.search;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import com.vsm.business.domain.PriceInfo;
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
 * Spring Data Elasticsearch repository for the {@link PriceInfo} entity.
 */
public interface PriceInfoSearchRepository extends ElasticsearchRepository<PriceInfo, Long>, PriceInfoSearchRepositoryInternal {}

interface PriceInfoSearchRepositoryInternal {
    Page<PriceInfo> search(String query, Pageable pageable);
}

class PriceInfoSearchRepositoryInternalImpl implements PriceInfoSearchRepositoryInternal {

    private final ElasticsearchRestTemplate elasticsearchTemplate;

    PriceInfoSearchRepositoryInternalImpl(ElasticsearchRestTemplate elasticsearchTemplate) {
        this.elasticsearchTemplate = elasticsearchTemplate;
    }

    @Override
    public Page<PriceInfo> search(String query, Pageable pageable) {
        NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(queryStringQuery(query));
        nativeSearchQuery.setPageable(pageable);
        List<PriceInfo> hits = elasticsearchTemplate
            .search(nativeSearchQuery, PriceInfo.class)
            .map(SearchHit::getContent)
            .stream()
            .collect(Collectors.toList());

        return new PageImpl<>(hits, pageable, hits.size());
    }
}
