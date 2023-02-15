package com.vsm.business.service.custom;

import com.vsm.business.repository.CategoryDataRepository;
import com.vsm.business.repository.search.CategoryDataSearchRepository;
import com.vsm.business.service.dto.CategoryDataDTO;
import com.vsm.business.service.mapper.CategoryDataMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryDataCustomService {
    private final Logger log = LoggerFactory.getLogger(CategoryDataCustomService.class);

    private final CategoryDataRepository categoryDataRepository;

    private final CategoryDataMapper categoryDataMapper;

    private final CategoryDataSearchRepository categoryDataSearchRepository;

    public CategoryDataCustomService(
        CategoryDataRepository categoryDataRepository,
        CategoryDataMapper categoryDataMapper,
        CategoryDataSearchRepository categoryDataSearchRepository
    ) {
        this.categoryDataRepository = categoryDataRepository;
        this.categoryDataMapper = categoryDataMapper;
        this.categoryDataSearchRepository = categoryDataSearchRepository;
    }

    public List<CategoryDataDTO> findAllByCategoryGroupId(Long categoryGroupId) {
        log.debug("CategoryDataCustomService findAllByCategoryGroupId({})", categoryGroupId);
        return categoryDataRepository.findAllByCategoryGroupId(categoryGroupId).stream().map(categoryDataMapper::toDto).collect(Collectors.toList());
    }

    public List<CategoryDataDTO> delete(List<CategoryDataDTO> categoryDataDTOList) {
        log.debug("CategoryDataCustomService delete({})", categoryDataDTOList);
        categoryDataRepository.deleteAllById(categoryDataDTOList.stream().map(categoryDataDTO -> categoryDataDTO.getId()).collect(Collectors.toList()));
        categoryDataSearchRepository.deleteAllById(categoryDataDTOList.stream().map(categoryDataDTO -> categoryDataDTO.getId()).collect(Collectors.toList()));
        return categoryDataRepository.findAll().stream().map(categoryDataMapper::toDto).collect(Collectors.toList());
    }

    public List<CategoryDataDTO> saveAll(List<CategoryDataDTO> categoryDataDTOList) {
        List<CategoryDataDTO> result = categoryDataRepository.saveAll(categoryDataDTOList.stream().map(categoryDataMapper::toEntity).collect(Collectors.toList())).stream().map(categoryDataMapper::toDto).collect(Collectors.toList());
        log.debug("CategoryDataCustomService saveAll({}) {}", categoryDataDTOList, result);
        return result;
    }
}
