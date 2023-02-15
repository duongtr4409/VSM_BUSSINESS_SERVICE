package com.vsm.business.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.vsm.business.domain.RequestRecall;
import com.vsm.business.repository.RequestRecallRepository;
import com.vsm.business.repository.search.RequestRecallSearchRepository;
import com.vsm.business.service.RequestRecallService;
import com.vsm.business.service.dto.RequestRecallDTO;
import com.vsm.business.service.mapper.RequestRecallMapper;
import java.util.Optional;

import org.hibernate.exception.GenericJDBCException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.UncategorizedElasticsearchException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link RequestRecall}.
 */
@Service
@Transactional
public class RequestRecallServiceImpl implements RequestRecallService {

    private final Logger log = LoggerFactory.getLogger(RequestRecallServiceImpl.class);

    private final RequestRecallRepository requestRecallRepository;

    private final RequestRecallMapper requestRecallMapper;

    private final RequestRecallSearchRepository requestRecallSearchRepository;

    public RequestRecallServiceImpl(
        RequestRecallRepository requestRecallRepository,
        RequestRecallMapper requestRecallMapper,
        RequestRecallSearchRepository requestRecallSearchRepository
    ) {
        this.requestRecallRepository = requestRecallRepository;
        this.requestRecallMapper = requestRecallMapper;
        this.requestRecallSearchRepository = requestRecallSearchRepository;
    }

    @Override
    public RequestRecallDTO save(RequestRecallDTO requestRecallDTO) {
        log.debug("Request to save RequestRecall : {}", requestRecallDTO);
        RequestRecall requestRecall = requestRecallMapper.toEntity(requestRecallDTO);
        requestRecall = requestRecallRepository.save(requestRecall);
        RequestRecallDTO result = requestRecallMapper.toDto(requestRecall);
        try{
//            requestRecallSearchRepository.save(requestRecall);
        }catch (StackOverflowError | UncategorizedElasticsearchException | GenericJDBCException  e){

        }
        return result;
    }

    @Override
    public Optional<RequestRecallDTO> partialUpdate(RequestRecallDTO requestRecallDTO) {
        log.debug("Request to partially update RequestRecall : {}", requestRecallDTO);

        return requestRecallRepository
            .findById(requestRecallDTO.getId())
            .map(existingRequestRecall -> {
                requestRecallMapper.partialUpdate(existingRequestRecall, requestRecallDTO);

                return existingRequestRecall;
            })
            .map(requestRecallRepository::save)
            .map(savedRequestRecall -> {
                requestRecallSearchRepository.save(savedRequestRecall);

                return savedRequestRecall;
            })
            .map(requestRecallMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RequestRecallDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RequestRecalls");
        return requestRecallRepository.findAll(pageable).map(requestRecallMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RequestRecallDTO> findOne(Long id) {
        log.debug("Request to get RequestRecall : {}", id);
        return requestRecallRepository.findById(id).map(requestRecallMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete RequestRecall : {}", id);
        requestRecallRepository.deleteById(id);
        requestRecallSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RequestRecallDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of RequestRecalls for query {}", query);
        return requestRecallSearchRepository.search(query, pageable).map(requestRecallMapper::toDto);
    }
}
