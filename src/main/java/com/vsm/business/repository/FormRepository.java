package com.vsm.business.repository;

import com.vsm.business.domain.Form;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.vsm.business.domain.Request;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Form entity.
 */
@Repository
public interface FormRepository extends JpaRepository<Form, Long>, JpaSpecificationExecutor<Form> {
    @Query(
        value = "select distinct form from Form form left join fetch form.fields",
        countQuery = "select count(distinct form) from Form form"
    )
    Page<Form> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct form from Form form left join fetch form.fields")
    List<Form> findAllWithEagerRelationships();

    @Query("select form from Form form left join fetch form.fields where form.id =:id")
    Optional<Form> findOneWithEagerRelationships(@Param("id") Long id);

    List<Form> findAllByRequestsIn(Set<Request> requests);

    List<Form> findAllByRequestsInAndIsDeleteNot(Set<Request> requests, boolean isDelete);
}
