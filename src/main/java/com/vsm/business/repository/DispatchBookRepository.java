package com.vsm.business.repository;

import com.vsm.business.domain.DispatchBook;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DispatchBook entity.
 */
@Repository
public interface DispatchBookRepository extends JpaRepository<DispatchBook, Long>, JpaSpecificationExecutor<DispatchBook> {
    @Query(
        value = "select distinct dispatchBook from DispatchBook dispatchBook left join fetch dispatchBook.dispatchBookOrgs",
        countQuery = "select count(distinct dispatchBook) from DispatchBook dispatchBook"
    )
    Page<DispatchBook> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct dispatchBook from DispatchBook dispatchBook left join fetch dispatchBook.dispatchBookOrgs")
    List<DispatchBook> findAllWithEagerRelationships();

    @Query("select dispatchBook from DispatchBook dispatchBook left join fetch dispatchBook.dispatchBookOrgs where dispatchBook.id =:id")
    Optional<DispatchBook> findOneWithEagerRelationships(@Param("id") Long id);
}
