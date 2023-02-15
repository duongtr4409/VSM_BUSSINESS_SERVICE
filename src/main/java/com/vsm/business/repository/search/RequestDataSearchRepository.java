package com.vsm.business.repository.search;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import com.vsm.business.domain.RequestData;
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
 * Spring Data Elasticsearch repository for the {@link RequestData} entity.
 */
public interface RequestDataSearchRepository extends ElasticsearchRepository<RequestData, Long>, RequestDataSearchRepositoryInternal {}

interface RequestDataSearchRepositoryInternal {
    Page<RequestData> search(String query, Pageable pageable);
}

class RequestDataSearchRepositoryInternalImpl implements RequestDataSearchRepositoryInternal {

    private final ElasticsearchRestTemplate elasticsearchTemplate;

    RequestDataSearchRepositoryInternalImpl(ElasticsearchRestTemplate elasticsearchTemplate) {
        this.elasticsearchTemplate = elasticsearchTemplate;
    }

    @Override
    public Page<RequestData> search(String query, Pageable pageable) {
        NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(queryStringQuery(query));
        nativeSearchQuery.setPageable(pageable);
        List<RequestData> hits = elasticsearchTemplate
            .search(nativeSearchQuery, RequestData.class)
            .map(SearchHit::getContent)
            .stream()
            .collect(Collectors.toList());

        return new PageImpl<>(hits, pageable, hits.size());
    }
}
