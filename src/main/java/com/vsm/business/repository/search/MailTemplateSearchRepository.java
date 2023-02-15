package com.vsm.business.repository.search;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import com.vsm.business.domain.MailTemplate;
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
 * Spring Data Elasticsearch repository for the {@link MailTemplate} entity.
 */
public interface MailTemplateSearchRepository extends ElasticsearchRepository<MailTemplate, Long>, MailTemplateSearchRepositoryInternal {}

interface MailTemplateSearchRepositoryInternal {
    Page<MailTemplate> search(String query, Pageable pageable);
}

class MailTemplateSearchRepositoryInternalImpl implements MailTemplateSearchRepositoryInternal {

    private final ElasticsearchRestTemplate elasticsearchTemplate;

    MailTemplateSearchRepositoryInternalImpl(ElasticsearchRestTemplate elasticsearchTemplate) {
        this.elasticsearchTemplate = elasticsearchTemplate;
    }

    @Override
    public Page<MailTemplate> search(String query, Pageable pageable) {
        NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(queryStringQuery(query));
        nativeSearchQuery.setPageable(pageable);
        List<MailTemplate> hits = elasticsearchTemplate
            .search(nativeSearchQuery, MailTemplate.class)
            .map(SearchHit::getContent)
            .stream()
            .collect(Collectors.toList());

        return new PageImpl<>(hits, pageable, hits.size());
    }
}
