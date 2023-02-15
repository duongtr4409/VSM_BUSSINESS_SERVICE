package com.vsm.business.repository.search;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import com.vsm.business.domain.Feature;
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
 * Spring Data Elasticsearch repository for the {@link Feature} entity.
 */
public interface FeatureSearchRepository extends ElasticsearchRepository<Feature, Long>, FeatureSearchRepositoryInternal {}

interface FeatureSearchRepositoryInternal {
    Page<Feature> search(String query, Pageable pageable);
}

class FeatureSearchRepositoryInternalImpl implements FeatureSearchRepositoryInternal {

    private final ElasticsearchRestTemplate elasticsearchTemplate;

    FeatureSearchRepositoryInternalImpl(ElasticsearchRestTemplate elasticsearchTemplate) {
        this.elasticsearchTemplate = elasticsearchTemplate;
    }

    @Override
    public Page<Feature> search(String query, Pageable pageable) {
        NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(queryStringQuery(query));
        nativeSearchQuery.setPageable(pageable);
        List<Feature> hits = elasticsearchTemplate
            .search(nativeSearchQuery, Feature.class)
            .map(SearchHit::getContent)
            .stream()
            .collect(Collectors.toList());

        return new PageImpl<>(hits, pageable, hits.size());
    }
}
