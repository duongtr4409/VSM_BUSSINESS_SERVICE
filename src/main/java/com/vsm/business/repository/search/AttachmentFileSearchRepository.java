package com.vsm.business.repository.search;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import com.vsm.business.domain.AttachmentFile;
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
 * Spring Data Elasticsearch repository for the {@link AttachmentFile} entity.
 */
public interface AttachmentFileSearchRepository
    extends ElasticsearchRepository<AttachmentFile, Long>, AttachmentFileSearchRepositoryInternal {}

interface AttachmentFileSearchRepositoryInternal {
    Page<AttachmentFile> search(String query, Pageable pageable);
}

class AttachmentFileSearchRepositoryInternalImpl implements AttachmentFileSearchRepositoryInternal {

    private final ElasticsearchRestTemplate elasticsearchTemplate;

    AttachmentFileSearchRepositoryInternalImpl(ElasticsearchRestTemplate elasticsearchTemplate) {
        this.elasticsearchTemplate = elasticsearchTemplate;
    }

    @Override
    public Page<AttachmentFile> search(String query, Pageable pageable) {
        NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(queryStringQuery(query));
        nativeSearchQuery.setPageable(pageable);
        List<AttachmentFile> hits = elasticsearchTemplate
            .search(nativeSearchQuery, AttachmentFile.class)
            .map(SearchHit::getContent)
            .stream()
            .collect(Collectors.toList());

        return new PageImpl<>(hits, pageable, hits.size());
    }
}
