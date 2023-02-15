package com.vsm.business.repository.search;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import com.vsm.business.domain.DelegateInfo;
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
 * Spring Data Elasticsearch repository for the {@link DelegateInfo} entity.
 */
public interface DelegateInfoSearchRepository extends ElasticsearchRepository<DelegateInfo, Long>, DelegateInfoSearchRepositoryInternal {}

interface DelegateInfoSearchRepositoryInternal {
    Page<DelegateInfo> search(String query, Pageable pageable);
}

class DelegateInfoSearchRepositoryInternalImpl implements DelegateInfoSearchRepositoryInternal {

    private final ElasticsearchRestTemplate elasticsearchTemplate;

    DelegateInfoSearchRepositoryInternalImpl(ElasticsearchRestTemplate elasticsearchTemplate) {
        this.elasticsearchTemplate = elasticsearchTemplate;
    }

    @Override
    public Page<DelegateInfo> search(String query, Pageable pageable) {
        NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(queryStringQuery(query));
        nativeSearchQuery.setPageable(pageable);
        List<DelegateInfo> hits = elasticsearchTemplate
            .search(nativeSearchQuery, DelegateInfo.class)
            .map(SearchHit::getContent)
            .stream()
            .collect(Collectors.toList());

        return new PageImpl<>(hits, pageable, hits.size());
    }
}
