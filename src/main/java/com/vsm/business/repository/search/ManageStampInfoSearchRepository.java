package com.vsm.business.repository.search;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import com.vsm.business.domain.ManageStampInfo;
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
 * Spring Data Elasticsearch repository for the {@link ManageStampInfo} entity.
 */
public interface ManageStampInfoSearchRepository
    extends ElasticsearchRepository<ManageStampInfo, Long>, ManageStampInfoSearchRepositoryInternal {}

interface ManageStampInfoSearchRepositoryInternal {
    Page<ManageStampInfo> search(String query, Pageable pageable);
}

class ManageStampInfoSearchRepositoryInternalImpl implements ManageStampInfoSearchRepositoryInternal {

    private final ElasticsearchRestTemplate elasticsearchTemplate;

    ManageStampInfoSearchRepositoryInternalImpl(ElasticsearchRestTemplate elasticsearchTemplate) {
        this.elasticsearchTemplate = elasticsearchTemplate;
    }

    @Override
    public Page<ManageStampInfo> search(String query, Pageable pageable) {
        NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(queryStringQuery(query));
        nativeSearchQuery.setPageable(pageable);
        List<ManageStampInfo> hits = elasticsearchTemplate
            .search(nativeSearchQuery, ManageStampInfo.class)
            .map(SearchHit::getContent)
            .stream()
            .collect(Collectors.toList());

        return new PageImpl<>(hits, pageable, hits.size());
    }
}
