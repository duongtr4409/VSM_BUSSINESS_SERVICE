package com.vsm.business.repository;

import com.vsm.business.domain.AttachmentFile;
import com.vsm.business.domain.RequestData;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data SQL repository for the AttachmentFile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AttachmentFileRepository extends JpaRepository<AttachmentFile, Long>, JpaSpecificationExecutor<AttachmentFile> {
    List<AttachmentFile> findAllByTemplateFormId(Long templateFormId);
    List<AttachmentFile> findAllByRequestDataId(Long requestDataId);
    List<AttachmentFile> findAllByManageStampInfoId(Long manageStampInfoId);

    List<AttachmentFile> findAllByMailTemplateId(Long mailTemplateId);
    List<AttachmentFile> findAllByIdInFileService(String idInFileService);
    List<AttachmentFile> findAllByFileName(String fileName);
    void deleteAllByTemplateFormId(Long templateFormId);
    List<AttachmentFile> findAllByItemId365(String itemId365);

    List<AttachmentFile> findAllByParentId(Long parentId);

    @Query(value = "with RECURSIVE cte as ( " +
        "select * From attachment_file where id = :id " +
        "union all " +
        "select att.* from attachment_file att inner join cte on att.parent_id = cte.id " +
        ")select * From cte ", nativeQuery = true)
    List<AttachmentFile> showFolderContent(@Param("id") Long id);

//    @Modifying
//    @Query(value = "with recursive cte as ( " +
//        "select * From attachment_file where id = :id " +
//        "union all " +
//        "select at.* from attachment_file at inner join cte on at.parent_id = cte.id " +
//        ") update attachment_file at set is_delete = true from cte where at.id = cte.id", nativeQuery = true)
    // thay đổi do bây giờ sẽ xóa thành thay vì thay đổi cờ is_delete
    @Modifying
    @Query(value = "with recursive cte as ( " +
        "select * From attachment_file where id = :id " +
        "union all " +
        "select at.* from attachment_file at inner join cte on at.parent_id = cte.id " +
        ")  delete from attachment_file at where at.id in (select id from cte)", nativeQuery = true)
    void deleteFolder(@Param("id") Long id);

    @Modifying
    @Query(value = "with recursive cte as ( " +
        "select * From attachment_file where id = :id " +
        "union all " +
        "select at.* from attachment_file at inner join cte on at.parent_id = cte.id " +
        ") update attachment_file at set is_delete = false from cte where at.id = cte.id", nativeQuery = true)
    void restoreFolder(@Param("id") Long id);

    List<AttachmentFile> findAttachmentFilesByTemplateFormIdAndRequestData(Long templateFormId, RequestData requestData);

    List<AttachmentFile> findAllByTemplateFormIdAndRequestDataId(Long templateFormId, Long requestDataId);

    /**
     * Hàm thực hiện lấy danh sách cha, ông,(folder chứa) ... của file
     * @param id    : id của file cần lấy danh sách các cha, ông
     * @return      : danh sách folder chứa
     */
    @Query(value = "with RECURSIVE cte as ( " +
        " select * from attachment_file where id = :id " +
        " union all " +
        " select att.* from attachment_file att inner join cte on att.id = cte.parent_id " +
        ")select * from cte ", nativeQuery = true)
    List<AttachmentFile> getAllParent(@Param("id") Long id);

    /**
     * Hàm thựuc hiện lấy AttachmentFile là cha to nhất (ở mức là con của parentId truyền vào)
     * @param id            : id của attahcmentFile cần tìm cha (ông , ...)
     * @param parentId      : id cả attachmentFile giới hạn (AttachmentFile cần tim phải là con cả id này)
     * @return  : Optional AttachmentFile cần tìm
     */
    @Query(value = "with RECURSIVE cte as ( " +
        " select * from attachment_file where id = :id " +
        " union all " +
        " select att.* from attachment_file att inner join cte on att.id = cte.parent_id " +
        ")select * from cte where cte.parent_id = :parentId", nativeQuery = true)
    Optional<AttachmentFile> getAllParent(@Param("id") Long id, @Param("parentId") Long parentId);

    Optional<AttachmentFile> findByRequestDataIdAndIsFolder(Long requestDataId, Boolean isFolder);
}
