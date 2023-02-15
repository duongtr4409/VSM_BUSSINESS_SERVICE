package com.vsm.business.repository.search;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import com.vsm.business.domain.OfficialDispatch;
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
 * Spring Data Elasticsearch repository for the {@link OfficialDispatch} entity.
 */
public interface OfficialDispatchSearchRepository
    extends ElasticsearchRepository<OfficialDispatch, Long>, OfficialDispatchSearchRepositoryInternal {}

interface OfficialDispatchSearchRepositoryInternal {
    Page<OfficialDispatch> search(String query, Pageable pageable);
}

class OfficialDispatchSearchRepositoryInternalImpl implements OfficialDispatchSearchRepositoryInternal {

    private final ElasticsearchRestTemplate elasticsearchTemplate;

    OfficialDispatchSearchRepositoryInternalImpl(ElasticsearchRestTemplate elasticsearchTemplate) {
        this.elasticsearchTemplate = elasticsearchTemplate;
    }

    @Override
    public Page<OfficialDispatch> search(String query, Pageable pageable) {
        NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(queryStringQuery(query));
        nativeSearchQuery.setPageable(pageable);
        List<OfficialDispatch> hits = elasticsearchTemplate
            .search(nativeSearchQuery, OfficialDispatch.class)
            .map(SearchHit::getContent)
            .stream()
            .collect(Collectors.toList());

        return new PageImpl<>(hits, pageable, hits.size());
    }
}
