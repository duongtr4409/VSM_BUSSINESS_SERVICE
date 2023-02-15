package com.vsm.business.repository;

import com.vsm.business.domain.CategoryGroup;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data SQL repository for the CategoryGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CategoryGroupRepository extends JpaRepository<CategoryGroup, Long>, JpaSpecificationExecutor<CategoryGroup> {

    List<CategoryGroup> findAllByNameAndParent(String name, CategoryGroup parent);

    List<CategoryGroup> findAllByCode(String code);

    List<CategoryGroup> findAllByCodeAndParentId(String code, Long parentId);

    @Query(value = "with RECURSIVE cte as ( " +
        "select * From category_group where parent_id is null " +
        "union all " +
        "select cg.* from category_group cg inner join cte on cg.parent_id = cte.id " +
        ")select * From cte", nativeQuery = true)
    List<CategoryGroup> findALlCustom();

    @Query(value = "with RECURSIVE cte as ( " +
        "select * From category_group where id = :id " +
        "union all " +
        "select cg.* from category_group cg inner join cte on cg.parent_id = cte.id " +
        ")select * From cte", nativeQuery = true)
    List<CategoryGroup> findChild(@Param("id") Long id);

    List<CategoryGroup> findAllByParentId(Long parentId);

    List<CategoryGroup> findAllByParentId(Long parentId, Pageable pageable);

    @Query(value = "select cg.* from category_group cg where parent_id = :parentId and (is_delete is null or is_delete = false) order by id offset :offset limit :limit",
    nativeQuery = true)
    List<CategoryGroup> getAllByParentId(@Param("parentId") Long parentId, @Param("offset") int offset, @Param("limit") int limit);

    @Query(value = "select count(1) from category_group cg where parent_id = :parentId and (is_delete is null or is_delete = false)",
        nativeQuery = true)
    Long countAllByParentId(@Param("parentId") Long parentId);

    List<CategoryGroup> findAllByChildrenIsNotNull();

    List<CategoryGroup> findAllByTennantCode(String tennatCode);

    @Query("select p from CategoryGroup p where p.children is not EMPTY")
    List<CategoryGroup> getAllChildrenIsNotNull();


    @Query(value = "with RECURSIVE cte as ( " +
        "select cate.id, cate.code, cate.name, cate.description, cate.created_name, cate.created_org_name, cate.created_rank_name, " +
        "cate.created_date, cate.modified_name, cate.modified_date, cate.is_active, cate.is_delete, cate.tennant_code, cate.tennant_name, " +
        "null \"tennant_id\", null \"created_id\", null \"modified_id\", cate.parent_id From category_group cate where parent_id is null " +
        "union all " +
        "select cg.id, cg.code, cg.name, cg.description, cg.created_name, cg.created_org_name, cg.created_rank_name, " +
        "cg.created_date, cg.modified_name, cg.modified_date, cg.is_active, cg.is_delete, cg.tennant_code, cg.tennant_name, " +
        "null \"tennant_id\", null \"created_id\", null \"modified_id\", cg.parent_id from category_group cg inner join cte on cg.parent_id = cte.id " +
        ")select * From cte", nativeQuery = true)
    List<CategoryGroup> findALlCustomIgnoreField();

    @Query(value = "select * from category_group where " +
        "((:id is null and parent_id is null ) " +
        "or (:id is not null and parent_id = :id)) " +
        "and upper(name) like :name order by name asc ", nativeQuery = true)
    List<CategoryGroup> getAllByParentIdAndName(@Param("id") Long id, @Param("name") String name);


    @Query(value = "select * from category_group where " +
        "((:id is null and parent_id is null ) " +
        "or (:id is not null and parent_id = :id)) " +
        "and upper(name) like :name order by name asc limit :limit offset :offset ", nativeQuery = true)
    List<CategoryGroup> getAllByParentIdAndName(@Param("id") Long id, @Param("name") String name, @Param("limit") int limit, @Param("offset") int offset);
}
