package com.vsm.business.repository;

import com.vsm.business.domain.Request;
import com.vsm.business.domain.TemplateForm;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TemplateForm entity.
 */
@Repository
public interface TemplateFormRepository extends JpaRepository<TemplateForm, Long>, JpaSpecificationExecutor<TemplateForm> {
    @Query(
        value = "select distinct templateForm from TemplateForm templateForm left join fetch templateForm.organizations",
        countQuery = "select count(distinct templateForm) from TemplateForm templateForm"
    )
    Page<TemplateForm> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct templateForm from TemplateForm templateForm left join fetch templateForm.organizations")
    List<TemplateForm> findAllWithEagerRelationships();

    @Query("select templateForm from TemplateForm templateForm left join fetch templateForm.organizations where templateForm.id =:id")
    Optional<TemplateForm> findOneWithEagerRelationships(@Param("id") Long id);

    List<TemplateForm> findAllByRequestsIn(Set<Request> requests);

    List<TemplateForm> findAllByTemplateFormCode(String templateFormCode);

    @Query(value = "select * from template_form where id in (" +
        "select DISTINCT(template_form_id) from attachment_file)", nativeQuery = true)
    List<TemplateForm> customFindAllHasFile();
}
