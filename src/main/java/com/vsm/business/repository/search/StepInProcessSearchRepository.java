package com.vsm.business.repository.search;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import com.vsm.business.domain.StepInProcess;
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
 * Spring Data Elasticsearch repository for the {@link StepInProcess} entity.
 */
public interface StepInProcessSearchRepository
    extends ElasticsearchRepository<StepInProcess, Long>, StepInProcessSearchRepositoryInternal {}

interface StepInProcessSearchRepositoryInternal {
    Page<StepInProcess> search(String query, Pageable pageable);
}

class StepInProcessSearchRepositoryInternalImpl implements StepInProcessSearchRepositoryInternal {

    private final ElasticsearchRestTemplate elasticsearchTemplate;

    StepInProcessSearchRepositoryInternalImpl(ElasticsearchRestTemplate elasticsearchTemplate) {
        this.elasticsearchTemplate = elasticsearchTemplate;
    }

    @Override
    public Page<StepInProcess> search(String query, Pageable pageable) {
        NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(queryStringQuery(query));
        nativeSearchQuery.setPageable(pageable);
        List<StepInProcess> hits = elasticsearchTemplate
            .search(nativeSearchQuery, StepInProcess.class)
            .map(SearchHit::getContent)
            .stream()
            .collect(Collectors.toList());

        return new PageImpl<>(hits, pageable, hits.size());
    }
}
