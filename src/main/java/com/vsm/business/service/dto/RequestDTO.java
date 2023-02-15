package com.vsm.business.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.vsm.business.domain.Request} entity.
 */
public class RequestDTO implements Serializable {

    private Long id;

    private String requestCode;

    private String requestName;

//    private String avatarPath;

    private String directoryPath;

    private String idDirectoryPath;

    private String description;

    private Boolean isCreateOutgoingDoc;

    private String ruleGenerateCode;

    private String ruleGenerateAttachName;

    @Lob
    private String mappingInfo;

    @Lob
    private String sapMapping;

    private String dataRoomPath;

    private String dataRoomId;

    private String dataRoomDriveId;

    private Boolean isSyncSap;

    private String createdName;

    private String createdOrgName;

    private String createdRankName;

    private Instant createdDate;

    private String modifiedName;

    private Instant modifiedDate;

    private Boolean isActive;

    private Boolean isDelete;

    private Long numberRequestData;

    private Long version;

    private String contractExpireFieldName;

    private String plotOfLandNumber;

    private String tennantCode;

    private String tennantName;

    private RequestTypeDTO requestType;

    private RequestGroupDTO requestGroup;

    private FormDTO form;

    private TennantDTO tennant;

    private UserInfoDTO created;

    private UserInfoDTO modified;

    private Set<TemplateFormDTO> templateForms = new HashSet<>();

    private Set<ProcessInfoDTO> processInfos = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(String requestCode) {
        this.requestCode = requestCode;
    }

    public String getRequestName() {
        return requestName;
    }

    public void setRequestName(String requestName) {
        this.requestName = requestName;
    }

//    public String getAvatarPath() {
//        return avatarPath;
//    }
//
//    public void setAvatarPath(String avatarPath) {
//        this.avatarPath = avatarPath;
//    }

    public String getDirectoryPath() {
        return directoryPath;
    }

    public void setDirectoryPath(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    public String getIdDirectoryPath() {
        return idDirectoryPath;
    }

    public void setIdDirectoryPath(String idDirectoryPath) {
        this.idDirectoryPath = idDirectoryPath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIsCreateOutgoingDoc() {
        return isCreateOutgoingDoc;
    }

    public void setIsCreateOutgoingDoc(Boolean isCreateOutgoingDoc) {
        this.isCreateOutgoingDoc = isCreateOutgoingDoc;
    }

    public String getRuleGenerateCode() {
        return ruleGenerateCode;
    }

    public void setRuleGenerateCode(String ruleGenerateCode) {
        this.ruleGenerateCode = ruleGenerateCode;
    }

    public String getRuleGenerateAttachName() {
        return ruleGenerateAttachName;
    }

    public void setRuleGenerateAttachName(String ruleGenerateAttachName) {
        this.ruleGenerateAttachName = ruleGenerateAttachName;
    }

    public String getMappingInfo() {
        return mappingInfo;
    }

    public void setMappingInfo(String mappingInfo) {
        this.mappingInfo = mappingInfo;
    }

    public String getSapMapping() {
        return sapMapping;
    }

    public void setSapMapping(String sapMapping) {
        this.sapMapping = sapMapping;
    }

    public String getDataRoomPath() {
        return dataRoomPath;
    }

    public void setDataRoomPath(String dataRoomPath) {
        this.dataRoomPath = dataRoomPath;
    }

    public String getDataRoomId() {
        return dataRoomId;
    }

    public void setDataRoomId(String dataRoomId) {
        this.dataRoomId = dataRoomId;
    }

    public String getDataRoomDriveId() {
        return dataRoomDriveId;
    }

    public void setDataRoomDriveId(String dataRoomDriveId) {
        this.dataRoomDriveId = dataRoomDriveId;
    }

    public Boolean getIsSyncSap() {
        return isSyncSap;
    }

    public void setIsSyncSap(Boolean isSyncSap) {
        this.isSyncSap = isSyncSap;
    }

    public String getCreatedName() {
        return createdName;
    }

    public void setCreatedName(String createdName) {
        this.createdName = createdName;
    }

    public String getCreatedOrgName() {
        return createdOrgName;
    }

    public void setCreatedOrgName(String createdOrgName) {
        this.createdOrgName = createdOrgName;
    }

    public String getCreatedRankName() {
        return createdRankName;
    }

    public void setCreatedRankName(String createdRankName) {
        this.createdRankName = createdRankName;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedName() {
        return modifiedName;
    }

    public void setModifiedName(String modifiedName) {
        this.modifiedName = modifiedName;
    }

    public Instant getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public Long getNumberRequestData() {
        return numberRequestData;
    }

    public void setNumberRequestData(Long numberRequestData) {
        this.numberRequestData = numberRequestData;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getContractExpireFieldName() {
        return contractExpireFieldName;
    }

    public void setContractExpireFieldName(String contractExpireFieldName) {
        this.contractExpireFieldName = contractExpireFieldName;
    }

    public String getPlotOfLandNumber() {
        return plotOfLandNumber;
    }

    public void setPlotOfLandNumber(String plotOfLandNumber) {
        this.plotOfLandNumber = plotOfLandNumber;
    }

    public String getTennantCode() {
        return tennantCode;
    }

    public void setTennantCode(String tennantCode) {
        this.tennantCode = tennantCode;
    }

    public String getTennantName() {
        return tennantName;
    }

    public void setTennantName(String tennantName) {
        this.tennantName = tennantName;
    }

    public RequestTypeDTO getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestTypeDTO requestType) {
        this.requestType = requestType;
    }

    public RequestGroupDTO getRequestGroup() {
        return requestGroup;
    }

    public void setRequestGroup(RequestGroupDTO requestGroup) {
        this.requestGroup = requestGroup;
    }

    public FormDTO getForm() {
        return form;
    }

    public void setForm(FormDTO form) {
        this.form = form;
    }

    public TennantDTO getTennant() {
        return tennant;
    }

    public void setTennant(TennantDTO tennant) {
        this.tennant = tennant;
    }

    public UserInfoDTO getCreated() {
        return created;
    }

    public void setCreated(UserInfoDTO created) {
        this.created = created;
    }

    public UserInfoDTO getModified() {
        return modified;
    }

    public void setModified(UserInfoDTO modified) {
        this.modified = modified;
    }

    public Set<TemplateFormDTO> getTemplateForms() {
        return templateForms;
    }

    public void setTemplateForms(Set<TemplateFormDTO> templateForms) {
        this.templateForms = templateForms;
    }

    public Set<ProcessInfoDTO> getProcessInfos() {
        return processInfos;
    }

    public void setProcessInfos(Set<ProcessInfoDTO> processInfos) {
        this.processInfos = processInfos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RequestDTO)) {
            return false;
        }

        RequestDTO requestDTO = (RequestDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, requestDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RequestDTO{" +
            "id=" + getId() +
            ", requestCode='" + getRequestCode() + "'" +
            ", requestName='" + getRequestName() + "'" +
//            ", avatarPath='" + getAvatarPath() + "'" +
            ", directoryPath='" + getDirectoryPath() + "'" +
            ", idDirectoryPath='" + getIdDirectoryPath() + "'" +
            ", description='" + getDescription() + "'" +
            ", isCreateOutgoingDoc='" + getIsCreateOutgoingDoc() + "'" +
            ", ruleGenerateCode='" + getRuleGenerateCode() + "'" +
            ", ruleGenerateAttachName='" + getRuleGenerateAttachName() + "'" +
            ", mappingInfo='" + getMappingInfo() + "'" +
            ", sapMapping='" + getSapMapping() + "'" +
            ", dataRoomPath='" + getDataRoomPath() + "'" +
            ", dataRoomId='" + getDataRoomId() + "'" +
            ", dataRoomDriveId='" + getDataRoomDriveId() + "'" +
            ", isSyncSap='" + getIsSyncSap() + "'" +
            ", createdName='" + getCreatedName() + "'" +
            ", createdOrgName='" + getCreatedOrgName() + "'" +
            ", createdRankName='" + getCreatedRankName() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", modifiedName='" + getModifiedName() + "'" +
            ", modifiedDate='" + getModifiedDate() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", isDelete='" + getIsDelete() + "'" +
            ", numberRequestData=" + getNumberRequestData() +
            ", version=" + getVersion() +
            ", contractExpireFieldName='" + getContractExpireFieldName() + "'" +
            ", plotOfLandNumber='" + getPlotOfLandNumber() + "'" +
            ", tennantCode='" + getTennantCode() + "'" +
            ", tennantName='" + getTennantName() + "'" +
            ", requestType=" + getRequestType() +
            ", requestGroup=" + getRequestGroup() +
            ", form=" + getForm() +
            ", tennant=" + getTennant() +
            ", created=" + getCreated() +
            ", modified=" + getModified() +
            ", templateForms=" + getTemplateForms() +
            ", processInfos=" + getProcessInfos() +
            "}";
    }
}
