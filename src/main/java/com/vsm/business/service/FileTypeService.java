package com.vsm.business.service;

import com.vsm.business.service.dto.FileTypeDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.vsm.business.domain.FileType}.
 */
public interface FileTypeService {
    /**
     * Save a fileType.
     *
     * @param fileTypeDTO the entity to save.
     * @return the persisted entity.
     */
    FileTypeDTO save(FileTypeDTO fileTypeDTO);

    /**
     * Partially updates a fileType.
     *
     * @param fileTypeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FileTypeDTO> partialUpdate(FileTypeDTO fileTypeDTO);

    /**
     * Get all the fileTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FileTypeDTO> findAll(Pageable pageable);

    /**
     * Get the "id" fileType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FileTypeDTO> findOne(Long id);

    /**
     * Delete the "id" fileType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the fileType corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FileTypeDTO> search(String query, Pageable pageable);
}
