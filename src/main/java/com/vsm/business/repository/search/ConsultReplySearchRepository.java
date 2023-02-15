package com.vsm.business.repository.search;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import com.vsm.business.domain.ConsultReply;
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
 * Spring Data Elasticsearch repository for the {@link ConsultReply} entity.
 */
public interface ConsultReplySearchRepository extends ElasticsearchRepository<ConsultReply, Long>, ConsultReplySearchRepositoryInternal {}

interface ConsultReplySearchRepositoryInternal {
    Page<ConsultReply> search(String query, Pageable pageable);
}

class ConsultReplySearchRepositoryInternalImpl implements ConsultReplySearchRepositoryInternal {

    private final ElasticsearchRestTemplate elasticsearchTemplate;

    ConsultReplySearchRepositoryInternalImpl(ElasticsearchRestTemplate elasticsearchTemplate) {
        this.elasticsearchTemplate = elasticsearchTemplate;
    }

    @Override
    public Page<ConsultReply> search(String query, Pageable pageable) {
        NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(queryStringQuery(query));
        nativeSearchQuery.setPageable(pageable);
        List<ConsultReply> hits = elasticsearchTemplate
            .search(nativeSearchQuery, ConsultReply.class)
            .map(SearchHit::getContent)
            .stream()
            .collect(Collectors.toList());

        return new PageImpl<>(hits, pageable, hits.size());
    }
}
