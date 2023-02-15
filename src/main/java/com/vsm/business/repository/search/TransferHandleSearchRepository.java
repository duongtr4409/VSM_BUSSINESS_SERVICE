package com.vsm.business.repository.search;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import com.vsm.business.domain.TransferHandle;
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
 * Spring Data Elasticsearch repository for the {@link TransferHandle} entity.
 */
public interface TransferHandleSearchRepository
    extends ElasticsearchRepository<TransferHandle, Long>, TransferHandleSearchRepositoryInternal {}

interface TransferHandleSearchRepositoryInternal {
    Page<TransferHandle> search(String query, Pageable pageable);
}

class TransferHandleSearchRepositoryInternalImpl implements TransferHandleSearchRepositoryInternal {

    private final ElasticsearchRestTemplate elasticsearchTemplate;

    TransferHandleSearchRepositoryInternalImpl(ElasticsearchRestTemplate elasticsearchTemplate) {
        this.elasticsearchTemplate = elasticsearchTemplate;
    }

    @Override
    public Page<TransferHandle> search(String query, Pageable pageable) {
        NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(queryStringQuery(query));
        nativeSearchQuery.setPageable(pageable);
        List<TransferHandle> hits = elasticsearchTemplate
            .search(nativeSearchQuery, TransferHandle.class)
            .map(SearchHit::getContent)
            .stream()
            .collect(Collectors.toList());

        return new PageImpl<>(hits, pageable, hits.size());
    }
}
