package com.vsm.business.repository.search;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import com.vsm.business.domain.ThemeConfig;
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
 * Spring Data Elasticsearch repository for the {@link ThemeConfig} entity.
 */
public interface ThemeConfigSearchRepository extends ElasticsearchRepository<ThemeConfig, Long>, ThemeConfigSearchRepositoryInternal {}

interface ThemeConfigSearchRepositoryInternal {
    Page<ThemeConfig> search(String query, Pageable pageable);
}

class ThemeConfigSearchRepositoryInternalImpl implements ThemeConfigSearchRepositoryInternal {

    private final ElasticsearchRestTemplate elasticsearchTemplate;

    ThemeConfigSearchRepositoryInternalImpl(ElasticsearchRestTemplate elasticsearchTemplate) {
        this.elasticsearchTemplate = elasticsearchTemplate;
    }

    @Override
    public Page<ThemeConfig> search(String query, Pageable pageable) {
        NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(queryStringQuery(query));
        nativeSearchQuery.setPageable(pageable);
        List<ThemeConfig> hits = elasticsearchTemplate
            .search(nativeSearchQuery, ThemeConfig.class)
            .map(SearchHit::getContent)
            .stream()
            .collect(Collectors.toList());

        return new PageImpl<>(hits, pageable, hits.size());
    }
}
