package com.vsm.business.repository.search;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import com.vsm.business.domain.UserInfo;
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
 * Spring Data Elasticsearch repository for the {@link UserInfo} entity.
 */
public interface UserInfoSearchRepository extends ElasticsearchRepository<UserInfo, Long>, UserInfoSearchRepositoryInternal {}

interface UserInfoSearchRepositoryInternal {
    Page<UserInfo> search(String query, Pageable pageable);
}

class UserInfoSearchRepositoryInternalImpl implements UserInfoSearchRepositoryInternal {

    private final ElasticsearchRestTemplate elasticsearchTemplate;

    UserInfoSearchRepositoryInternalImpl(ElasticsearchRestTemplate elasticsearchTemplate) {
        this.elasticsearchTemplate = elasticsearchTemplate;
    }

    @Override
    public Page<UserInfo> search(String query, Pageable pageable) {
        NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(queryStringQuery(query));
        nativeSearchQuery.setPageable(pageable);
        List<UserInfo> hits = elasticsearchTemplate
            .search(nativeSearchQuery, UserInfo.class)
            .map(SearchHit::getContent)
            .stream()
            .collect(Collectors.toList());

        return new PageImpl<>(hits, pageable, hits.size());
    }
}
