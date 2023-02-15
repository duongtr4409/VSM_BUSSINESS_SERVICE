package com.vsm.business.repository.search;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import com.vsm.business.domain.OfficialDispatchStatus;
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
 * Spring Data Elasticsearch repository for the {@link OfficialDispatchStatus} entity.
 */
public interface OfficialDispatchStatusSearchRepository
    extends ElasticsearchRepository<OfficialDispatchStatus, Long>, OfficialDispatchStatusSearchRepositoryInternal {}

interface OfficialDispatchStatusSearchRepositoryInternal {
    Page<OfficialDispatchStatus> search(String query, Pageable pageable);
}

class OfficialDispatchStatusSearchRepositoryInternalImpl implements OfficialDispatchStatusSearchRepositoryInternal {

    private final ElasticsearchRestTemplate elasticsearchTemplate;

    OfficialDispatchStatusSearchRepositoryInternalImpl(ElasticsearchRestTemplate elasticsearchTemplate) {
        this.elasticsearchTemplate = elasticsearchTemplate;
    }

    @Override
    public Page<OfficialDispatchStatus> search(String query, Pageable pageable) {
        NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(queryStringQuery(query));
        nativeSearchQuery.setPageable(pageable);
        List<OfficialDispatchStatus> hits = elasticsearchTemplate
            .search(nativeSearchQuery, OfficialDispatchStatus.class)
            .map(SearchHit::getContent)
            .stream()
            .collect(Collectors.toList());

        return new PageImpl<>(hits, pageable, hits.size());
    }
}
