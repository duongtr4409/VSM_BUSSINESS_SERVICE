package com.vsm.business.service.custom.file.bo;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class AttachmentMetadata implements Serializable {

    private String id;
    private String vbAttachmentId;
    private String objectId;
    private String objectType;
    private Long sizeTotal;
    private Long pageSize;
    private String status;
    private Long isDelete;
    private String creatorId;
    private String creatorName;
    private Date createTime;
    private String updatorId;
    private String updatorName;
    private Date updateTime;
    private String tenantCode;

    public AttachmentMetadata() {
    }

    public AttachmentMetadata(String id, String vbAttachmentId, String objectId, String objectType, Long sizeTotal, Long pageSize, String status, Long isDelete, String creatorId, String creatorName, Date createTime, String updatorId, String updatorName, Date updateTime, String tenantCode) {
        this.id = id;
        this.vbAttachmentId = vbAttachmentId;
        this.objectId = objectId;
        this.objectType = objectType;
        this.sizeTotal = sizeTotal;
        this.pageSize = pageSize;
        this.status = status;
        this.isDelete = isDelete;
        this.creatorId = creatorId;
        this.creatorName = creatorName;
        this.createTime = createTime;
        this.updatorId = updatorId;
        this.updatorName = updatorName;
        this.updateTime = updateTime;
        this.tenantCode = tenantCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVbAttachmentId() {
        return vbAttachmentId;
    }

    public void setVbAttachmentId(String vbAttachmentId) {
        this.vbAttachmentId = vbAttachmentId;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public Long getSizeTotal() {
        return sizeTotal;
    }

    public void setSizeTotal(Long sizeTotal) {
        this.sizeTotal = sizeTotal;
    }

    public Long getPageSize() {
        return pageSize;
    }

    public void setPageSize(Long pageSize) {
        this.pageSize = pageSize;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Long isDelete) {
        this.isDelete = isDelete;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdatorId() {
        return updatorId;
    }

    public void setUpdatorId(String updatorId) {
        this.updatorId = updatorId;
    }

    public String getUpdatorName() {
        return updatorName;
    }

    public void setUpdatorName(String updatorName) {
        this.updatorName = updatorName;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AttachmentMetadata that = (AttachmentMetadata) o;
        return Objects.equals(id, that.id) && Objects.equals(vbAttachmentId, that.vbAttachmentId) && Objects.equals(objectId, that.objectId) && Objects.equals(objectType, that.objectType) && Objects.equals(sizeTotal, that.sizeTotal) && Objects.equals(pageSize, that.pageSize) && Objects.equals(status, that.status) && Objects.equals(isDelete, that.isDelete) && Objects.equals(creatorId, that.creatorId) && Objects.equals(creatorName, that.creatorName) && Objects.equals(createTime, that.createTime) && Objects.equals(updatorId, that.updatorId) && Objects.equals(updatorName, that.updatorName) && Objects.equals(updateTime, that.updateTime) && Objects.equals(tenantCode, that.tenantCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, vbAttachmentId, objectId, objectType, sizeTotal, pageSize, status, isDelete, creatorId, creatorName, createTime, updatorId, updatorName, updateTime, tenantCode);
    }

    @Override
    public String toString() {
        return "AttachmentMetadata{" +
            "id='" + id + '\'' +
            ", vbAttachmentId='" + vbAttachmentId + '\'' +
            ", objectId='" + objectId + '\'' +
            ", objectType='" + objectType + '\'' +
            ", sizeTotal=" + sizeTotal +
            ", pageSize=" + pageSize +
            ", status='" + status + '\'' +
            ", isDelete=" + isDelete +
            ", creatorId='" + creatorId + '\'' +
            ", creatorName='" + creatorName + '\'' +
            ", createTime=" + createTime +
            ", updatorId='" + updatorId + '\'' +
            ", updatorName='" + updatorName + '\'' +
            ", updateTime=" + updateTime +
            ", tenantCode='" + tenantCode + '\'' +
            '}';
    }
}
