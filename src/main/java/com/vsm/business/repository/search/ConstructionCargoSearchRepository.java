package com.vsm.business.repository.search;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import com.vsm.business.domain.ConstructionCargo;
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
 * Spring Data Elasticsearch repository for the {@link ConstructionCargo} entity.
 */
public interface ConstructionCargoSearchRepository
    extends ElasticsearchRepository<ConstructionCargo, Long>, ConstructionCargoSearchRepositoryInternal {}

interface ConstructionCargoSearchRepositoryInternal {
    Page<ConstructionCargo> search(String query, Pageable pageable);
}

class ConstructionCargoSearchRepositoryInternalImpl implements ConstructionCargoSearchRepositoryInternal {

    private final ElasticsearchRestTemplate elasticsearchTemplate;

    ConstructionCargoSearchRepositoryInternalImpl(ElasticsearchRestTemplate elasticsearchTemplate) {
        this.elasticsearchTemplate = elasticsearchTemplate;
    }

    @Override
    public Page<ConstructionCargo> search(String query, Pageable pageable) {
        NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(queryStringQuery(query));
        nativeSearchQuery.setPageable(pageable);
        List<ConstructionCargo> hits = elasticsearchTemplate
            .search(nativeSearchQuery, ConstructionCargo.class)
            .map(SearchHit::getContent)
            .stream()
            .collect(Collectors.toList());

        return new PageImpl<>(hits, pageable, hits.size());
    }
}
