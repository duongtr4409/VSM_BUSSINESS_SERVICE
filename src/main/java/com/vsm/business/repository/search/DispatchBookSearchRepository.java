package com.vsm.business.repository.search;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import com.vsm.business.domain.DispatchBook;
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
 * Spring Data Elasticsearch repository for the {@link DispatchBook} entity.
 */
public interface DispatchBookSearchRepository extends ElasticsearchRepository<DispatchBook, Long>, DispatchBookSearchRepositoryInternal {}

interface DispatchBookSearchRepositoryInternal {
    Page<DispatchBook> search(String query, Pageable pageable);
}

class DispatchBookSearchRepositoryInternalImpl implements DispatchBookSearchRepositoryInternal {

    private final ElasticsearchRestTemplate elasticsearchTemplate;

    DispatchBookSearchRepositoryInternalImpl(ElasticsearchRestTemplate elasticsearchTemplate) {
        this.elasticsearchTemplate = elasticsearchTemplate;
    }

    @Override
    public Page<DispatchBook> search(String query, Pageable pageable) {
        NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(queryStringQuery(query));
        nativeSearchQuery.setPageable(pageable);
        List<DispatchBook> hits = elasticsearchTemplate
            .search(nativeSearchQuery, DispatchBook.class)
            .map(SearchHit::getContent)
            .stream()
            .collect(Collectors.toList());

        return new PageImpl<>(hits, pageable, hits.size());
    }
}
