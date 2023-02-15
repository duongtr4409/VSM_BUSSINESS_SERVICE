package com.vsm.business.repository.search;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import com.vsm.business.domain.StepProcessDoc;
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
 * Spring Data Elasticsearch repository for the {@link StepProcessDoc} entity.
 */
public interface StepProcessDocSearchRepository
    extends ElasticsearchRepository<StepProcessDoc, Long>, StepProcessDocSearchRepositoryInternal {}

interface StepProcessDocSearchRepositoryInternal {
    Page<StepProcessDoc> search(String query, Pageable pageable);
}

class StepProcessDocSearchRepositoryInternalImpl implements StepProcessDocSearchRepositoryInternal {

    private final ElasticsearchRestTemplate elasticsearchTemplate;

    StepProcessDocSearchRepositoryInternalImpl(ElasticsearchRestTemplate elasticsearchTemplate) {
        this.elasticsearchTemplate = elasticsearchTemplate;
    }

    @Override
    public Page<StepProcessDoc> search(String query, Pageable pageable) {
        NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(queryStringQuery(query));
        nativeSearchQuery.setPageable(pageable);
        List<StepProcessDoc> hits = elasticsearchTemplate
            .search(nativeSearchQuery, StepProcessDoc.class)
            .map(SearchHit::getContent)
            .stream()
            .collect(Collectors.toList());

        return new PageImpl<>(hits, pageable, hits.size());
    }
}
