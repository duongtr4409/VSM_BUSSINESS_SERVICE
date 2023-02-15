package com.vsm.business.repository;

import com.vsm.business.domain.MailTemplate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the MailTemplate entity.
 */
@Repository
public interface MailTemplateRepository extends JpaRepository<MailTemplate, Long>, JpaSpecificationExecutor<MailTemplate> {
    @Query(
        value = "select distinct mailTemplate from MailTemplate mailTemplate left join fetch mailTemplate.organizations left join fetch mailTemplate.processInfos",
        countQuery = "select count(distinct mailTemplate) from MailTemplate mailTemplate"
    )
    Page<MailTemplate> findAllWithEagerRelationships(Pageable pageable);

    @Query(
        "select distinct mailTemplate from MailTemplate mailTemplate left join fetch mailTemplate.organizations left join fetch mailTemplate.processInfos"
    )
    List<MailTemplate> findAllWithEagerRelationships();

    @Query(
        "select mailTemplate from MailTemplate mailTemplate left join fetch mailTemplate.organizations left join fetch mailTemplate.processInfos where mailTemplate.id =:id"
    )
    Optional<MailTemplate> findOneWithEagerRelationships(@Param("id") Long id);

    List<MailTemplate> findAllByMailTemplateCode(String mailTemplateCode);
}
