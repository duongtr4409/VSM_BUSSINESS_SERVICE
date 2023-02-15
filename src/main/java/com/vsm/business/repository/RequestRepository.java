package com.vsm.business.repository;

import com.vsm.business.domain.Request;
import java.util.List;
import java.util.Set;
import java.util.Optional;

import com.vsm.business.domain.RequestGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Request entity.
 */
@Repository
public interface RequestRepository extends JpaRepository<Request, Long>, JpaSpecificationExecutor<Request> {
    @Query(
        value = "select distinct request from Request request left join fetch request.templateForms left join fetch request.processInfos",
        countQuery = "select count(distinct request) from Request request"
    )
    Page<Request> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct request from Request request left join fetch request.templateForms left join fetch request.processInfos")
    List<Request> findAllWithEagerRelationships();

    @Query(
        "select request from Request request left join fetch request.templateForms left join fetch request.processInfos where request.id =:id"
    )
    Optional<Request> findOneWithEagerRelationships(@Param("id") Long id);

    List<Request> findAllByRequestTypeId(Long requestTypeId);

    List<Request> findAllByRequestGroup(RequestGroup requestGroup);

    //Set<Request> findAllByProcessInfos(Set<ProcessInfo> processInfos);

    @Query(value = "select * from request rq " +
        "inner join rel_request__template_form rqtp on rq.id = rqtp.request_id " +
        "where rqtp.template_form_id in :templateFormId", nativeQuery = true)
    List<Request> findAllByTemplateForms(@Param("templateFormId") Set<Long> templateFormId);

    List<Request> findAllByRequestGroupId(Long requestGroupId);

    @Query(value = "select re.id, re.request_code, re.request_name, re.directory_path, re.id_directory_path, re.description, " +
        " re.is_create_outgoing_doc, re.rule_generate_code, re.rule_generate_attach_name, re.mapping_info, " +
        " re.sap_mapping, re.data_room_path, re.data_room_id, re.data_room_drive_id, re.is_sync_sap, re.created_name, " +
        " re.created_org_name, re.created_rank_name, re.created_date, re.modified_name, re.modified_date, re.is_active, " +
        " re.is_delete, re.number_request_data, re.version, re.contract_expire_field_name, re.plot_of_land_number, re.tennant_code, re.tennant_name, " +
        "null \"request_type_id\", null \"request_group_id\", null \"form_id\", null \"tennant_id\", null \"created_id\", null \"modified_id\", null \"request_data_id\" From Request re", nativeQuery = true)
    List<Request> getAll();

    @Query(value = "select re.id, re.request_code, re.request_name, re.directory_path, re.id_directory_path, re.description, " +
        " re.is_create_outgoing_doc, re.rule_generate_code, re.rule_generate_attach_name, re.mapping_info, " +
        " re.sap_mapping, re.data_room_path, re.data_room_id, re.data_room_drive_id, re.is_sync_sap, re.created_name, " +
        " re.created_org_name, re.created_rank_name, re.created_date, re.modified_name, re.modified_date, re.is_active, " +
        " re.is_delete, re.number_request_data, re.version, re.contract_expire_field_name, re.plot_of_land_number, re.tennant_code, re.tennant_name, " +
        " re.request_type_id, re.request_group_id, null \"form_id\", null \"tennant_id\", null \"created_id\", re.modified_id, null \"request_data_id\" From Request re where request_group_id = :requestGroupId " +
        " and (is_delete is null or is_delete = false)", nativeQuery = true)
    List<Request> getAllByRequestGroupId(@Param("requestGroupId") Long requestGroupId);

    @Query(value = "select re.id, re.request_code, re.request_name, null as \"avatar_path\", " +
        "directory_path as \"directory_path\", null as \"id_directory_path\", null as \"description\", " +
        "null as \"is_create_outgoing_doc\", null as \"rule_generate_code\", null as \"mapping_info\", null as \"created_name\"," +
        "null as \"created_org_name\", null as \"created_rank_name\", null as \"created_date\", null as \"modified_name\", " +
        "null as \"modified_date\", null as \"is_active\", null as \"is_delete\", null as \"number_request_data\", " +
        "null as \"version\", null \"tennant_code\", null as \"tennant_name\", null as \"request_type_id\", null as \"request_group_id\", " +
        "null as \"form_id\", null as \"tennant_id\", null as \"created_id\", null as \"modified_id\", null as \"sap_mapping\", null as \"data_room_path\", null as \"data_room_id\", null as \"is_sync_sap\", " +
        "null as \"contract_number\", null as \"data_room_drive_id\", null as \"contract_expire_field_name\", null as \"plot_of_land_number\", null as \"rule_generate_attach_name\" " +
        " from request re where id in " +
        "(select request_id from rel_request__process_info where process_info_id = :processInfo)", nativeQuery = true)
    List<Request> getAllRequestByProcessInfo(@Param("processInfo") Long processInfo);

//    @Query(value = "select * from request where id in (select request_id from rel_request__process_info where " +
//        "process_info_id in (select process_info_id from rel_process_info__organization where " +
//        "organization_id in :organizationIds))"
//        , nativeQuery = true)
    @Query(value = "select request_id from rel_request__process_info where " +
        "process_info_id in (select process_info_id from rel_process_info__organization where " +
        "organization_id in :organizationIds)"
        , nativeQuery = true)
    Set<Long> getAllRequestIdsByOrganization(@Param("organizationIds") Set<Long> organizationIds);
}
