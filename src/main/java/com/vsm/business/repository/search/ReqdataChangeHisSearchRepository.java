package com.vsm.business.repository.search;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import com.vsm.business.domain.ReqdataChangeHis;
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
 * Spring Data Elasticsearch repository for the {@link ReqdataChangeHis} entity.
 */
public interface ReqdataChangeHisSearchRepository
    extends ElasticsearchRepository<ReqdataChangeHis, Long>, ReqdataChangeHisSearchRepositoryInternal {}

interface ReqdataChangeHisSearchRepositoryInternal {
    Page<ReqdataChangeHis> search(String query, Pageable pageable);
}

class ReqdataChangeHisSearchRepositoryInternalImpl implements ReqdataChangeHisSearchRepositoryInternal {

    private final ElasticsearchRestTemplate elasticsearchTemplate;

    ReqdataChangeHisSearchRepositoryInternalImpl(ElasticsearchRestTemplate elasticsearchTemplate) {
        this.elasticsearchTemplate = elasticsearchTemplate;
    }

    @Override
    public Page<ReqdataChangeHis> search(String query, Pageable pageable) {
        NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(queryStringQuery(query));
        nativeSearchQuery.setPageable(pageable);
        List<ReqdataChangeHis> hits = elasticsearchTemplate
            .search(nativeSearchQuery, ReqdataChangeHis.class)
            .map(SearchHit::getContent)
            .stream()
            .collect(Collectors.toList());

        return new PageImpl<>(hits, pageable, hits.size());
    }
}
