package com.vsm.business.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.*;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.vsm.business.domain.RequestData} entity.
 */
public class RequestDataDTO implements Serializable {

    private Long id;

    private String requestDataCode;

    private String requestDataName;

    private String directoryPath;

    private String idDirectoryPath;

    private String ruleGenerateAttachName;

    private Long numberAttach;

    private Long currentRound;

    private String title;

    @Lob
    private String description;

    private String signType;

    private String signTypeName;

    private String signTypeCode;

    private String requestTypeName;

    private String requestTypeCode;

    private String requestGroupName;

    private String requestGroupCode;

    private Boolean isCreateOutgoingDoc;

    private Boolean isApprove;

    private String approverName;

    private String approverOrgName;

    private String approverRankName;

    private Boolean isRevoked;

    private String revokerName;

    private String revokerOrgName;

    private String revokerRankName;

    private String statusName;

    private Long oldStatus;

    @Lob
    private String objectSchema;

    @Lob
    private String objectModel;

    private Instant expiredTime;

    private Boolean isDone;

    private Instant timeDone;

    @Lob
    private String mappingInfo;

    @Lob
    private String sapMapping;

    private String dataRoomPath;

    private String dataRoomId;

    private String dataRoomDriveId;

    private String contractNumber;

    private Boolean resultSyncContract;

    private Instant contractExpireTime;

    private String plotOfLandNumber;

    private Boolean isSyncSap;

    private String createdName;

    private String createdOrgName;

    private String createdRankName;

    private Instant createdDate;

    private String modifiedName;

    private Instant modifiedDate;

    private Boolean isActive;

    private Boolean isDelete;

    private String tennantCode;

    private String tennantName;

    private RequestDTO request;

    private StatusDTO status;

    private TennantDTO tennant;

    private RequestTypeDTO requestType;

    private RequestGroupDTO requestGroup;

    private OrganizationDTO organization;

    private UserInfoDTO created;

    private UserInfoDTO modified;

    private StatusDTO subStatus;

    private RequestDataDTO reqDataConcerned;

    private UserInfoDTO approver;

    private UserInfoDTO revoker;

    private Set<UserInfoDTO> userInfos = new HashSet<>();

    private List<RequestDataDTO> requestDataList = new ArrayList<>();

    private List<AttachmentFileDTO> attachmentFileList = new ArrayList<>();

    private ProcessInfoDTO processInfoDTO;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRequestDataCode() {
        return requestDataCode;
    }

    public void setRequestDataCode(String requestDataCode) {
        this.requestDataCode = requestDataCode;
    }

    public String getRequestDataName() {
        return requestDataName;
    }

    public void setRequestDataName(String requestDataName) {
        this.requestDataName = requestDataName;
    }

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

    public String getRuleGenerateAttachName() {
        return ruleGenerateAttachName;
    }

    public void setRuleGenerateAttachName(String ruleGenerateAttachName) {
        this.ruleGenerateAttachName = ruleGenerateAttachName;
    }

    public Long getNumberAttach() {
        return numberAttach;
    }

    public void setNumberAttach(Long numberAttach) {
        this.numberAttach = numberAttach;
    }

    public Long getCurrentRound() {
        return currentRound;
    }

    public void setCurrentRound(Long currentRound) {
        this.currentRound = currentRound;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getSignTypeName() {
        return signTypeName;
    }

    public void setSignTypeName(String signTypeName) {
        this.signTypeName = signTypeName;
    }

    public String getSignTypeCode() {
        return signTypeCode;
    }

    public void setSignTypeCode(String signTypeCode) {
        this.signTypeCode = signTypeCode;
    }

    public String getRequestTypeName() {
        return requestTypeName;
    }

    public void setRequestTypeName(String requestTypeName) {
        this.requestTypeName = requestTypeName;
    }

    public String getRequestTypeCode() {
        return requestTypeCode;
    }

    public void setRequestTypeCode(String requestTypeCode) {
        this.requestTypeCode = requestTypeCode;
    }

    public String getRequestGroupName() {
        return requestGroupName;
    }

    public void setRequestGroupName(String requestGroupName) {
        this.requestGroupName = requestGroupName;
    }

    public String getRequestGroupCode() {
        return requestGroupCode;
    }

    public void setRequestGroupCode(String requestGroupCode) {
        this.requestGroupCode = requestGroupCode;
    }

    public Boolean getIsCreateOutgoingDoc() {
        return isCreateOutgoingDoc;
    }

    public void setIsCreateOutgoingDoc(Boolean isCreateOutgoingDoc) {
        this.isCreateOutgoingDoc = isCreateOutgoingDoc;
    }

    public Boolean getIsApprove() {
        return isApprove;
    }

    public void setIsApprove(Boolean isApprove) {
        this.isApprove = isApprove;
    }

    public String getApproverName() {
        return approverName;
    }

    public void setApproverName(String approverName) {
        this.approverName = approverName;
    }

    public String getApproverOrgName() {
        return approverOrgName;
    }

    public void setApproverOrgName(String approverOrgName) {
        this.approverOrgName = approverOrgName;
    }

    public String getApproverRankName() {
        return approverRankName;
    }

    public void setApproverRankName(String approverRankName) {
        this.approverRankName = approverRankName;
    }

    public Boolean getIsRevoked() {
        return isRevoked;
    }

    public void setIsRevoked(Boolean isRevoked) {
        this.isRevoked = isRevoked;
    }

    public String getRevokerName() {
        return revokerName;
    }

    public void setRevokerName(String revokerName) {
        this.revokerName = revokerName;
    }

    public String getRevokerOrgName() {
        return revokerOrgName;
    }

    public void setRevokerOrgName(String revokerOrgName) {
        this.revokerOrgName = revokerOrgName;
    }

    public String getRevokerRankName() {
        return revokerRankName;
    }

    public void setRevokerRankName(String revokerRankName) {
        this.revokerRankName = revokerRankName;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public Long getOldStatus() {
        return oldStatus;
    }

    public void setOldStatus(Long oldStatus) {
        this.oldStatus = oldStatus;
    }

    public String getObjectSchema() {
        return objectSchema;
    }

    public void setObjectSchema(String objectSchema) {
        this.objectSchema = objectSchema;
    }

    public String getObjectModel() {
        return objectModel;
    }

    public void setObjectModel(String objectModel) {
        this.objectModel = objectModel;
    }

    public Instant getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(Instant expiredTime) {
        this.expiredTime = expiredTime;
    }

    public Boolean getIsDone() {
        return isDone;
    }

    public void setIsDone(Boolean isDone) {
        this.isDone = isDone;
    }

    public Instant getTimeDone() {
        return timeDone;
    }

    public void setTimeDone(Instant timeDone) {
        this.timeDone = timeDone;
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

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public Boolean getResultSyncContract(){
        return resultSyncContract;
    }

    public void setResultSyncContract(Boolean resultSyncContract){
        this.resultSyncContract = resultSyncContract;
    }

    public Instant getContractExpireTime() {
        return contractExpireTime;
    }

    public void setContractExpireTime(Instant contractExpireTime) {
        this.contractExpireTime = contractExpireTime;
    }

    public String getPlotOfLandNumber() {
        return plotOfLandNumber;
    }

    public void setPlotOfLandNumber(String plotOfLandNumber) {
        this.plotOfLandNumber = plotOfLandNumber;
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

    public RequestDTO getRequest() {
        return request;
    }

    public void setRequest(RequestDTO request) {
        this.request = request;
    }

    public StatusDTO getStatus() {
        return status;
    }

    public void setStatus(StatusDTO status) {
        this.status = status;
    }

    public TennantDTO getTennant() {
        return tennant;
    }

    public void setTennant(TennantDTO tennant) {
        this.tennant = tennant;
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

    public OrganizationDTO getOrganization() {
        return organization;
    }

    public void setOrganization(OrganizationDTO organization) {
        this.organization = organization;
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

    public StatusDTO getSubStatus() {
        return subStatus;
    }

    public void setSubStatus(StatusDTO subStatus) {
        this.subStatus = subStatus;
    }

    public RequestDataDTO getReqDataConcerned() {
        return reqDataConcerned;
    }

    public void setReqDataConcerned(RequestDataDTO reqDataConcerned) {
        this.reqDataConcerned = reqDataConcerned;
    }

    public UserInfoDTO getApprover() {
        return approver;
    }

    public void setApprover(UserInfoDTO approver) {
        this.approver = approver;
    }

    public UserInfoDTO getRevoker() {
        return revoker;
    }

    public void setRevoker(UserInfoDTO revoker) {
        this.revoker = revoker;
    }

    public Set<UserInfoDTO> getUserInfos() {
        return userInfos;
    }

    public void setUserInfos(Set<UserInfoDTO> userInfos) {
        this.userInfos = userInfos;
    }

	public List<RequestDataDTO> getRequestDataList() {
        return requestDataList;
    }

    public void setRequestDataList(List<RequestDataDTO> requestDataList) {
        this.requestDataList = requestDataList;
    }

    public List<AttachmentFileDTO> getAttachmentFileList() {
        return attachmentFileList;
    }

    public void setAttachmentFileList(List<AttachmentFileDTO> attachmentFileList) {
        this.attachmentFileList = attachmentFileList;
    }

    public ProcessInfoDTO getProcessInfoDTO() {
        return processInfoDTO;
    }

    public void setProcessInfoDTO(ProcessInfoDTO processInfoDTO) {
        this.processInfoDTO = processInfoDTO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RequestDataDTO)) {
            return false;
        }

        RequestDataDTO requestDataDTO = (RequestDataDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, requestDataDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RequestDataDTO{" +
            "id=" + getId() +
            ", requestDataCode='" + getRequestDataCode() + "'" +
            ", requestDataName='" + getRequestDataName() + "'" +
            ", directoryPath='" + getDirectoryPath() + "'" +
            ", idDirectoryPath='" + getIdDirectoryPath() + "'" +
            ", ruleGenerateAttachName='" + getRuleGenerateAttachName() + "'" +
            ", numberAttach=" + getNumberAttach() +
            ", currentRound=" + getCurrentRound() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", signType='" + getSignType() + "'" +
            ", signTypeName='" + getSignTypeName() + "'" +
            ", signTypeCode='" + getSignTypeCode() + "'" +
            ", requestTypeName='" + getRequestTypeName() + "'" +
            ", requestTypeCode='" + getRequestTypeCode() + "'" +
            ", requestGroupName='" + getRequestGroupName() + "'" +
            ", requestGroupCode='" + getRequestGroupCode() + "'" +
            ", isCreateOutgoingDoc='" + getIsCreateOutgoingDoc() + "'" +
            ", isApprove='" + getIsApprove() + "'" +
            ", approverName='" + getApproverName() + "'" +
            ", approverOrgName='" + getApproverOrgName() + "'" +
            ", approverRankName='" + getApproverRankName() + "'" +
            ", isRevoked='" + getIsRevoked() + "'" +
            ", revokerName='" + getRevokerName() + "'" +
            ", revokerOrgName='" + getRevokerOrgName() + "'" +
            ", revokerRankName='" + getRevokerRankName() + "'" +
            ", statusName='" + getStatusName() + "'" +
            ", oldStatus=" + getOldStatus() +
            ", objectSchema='" + getObjectSchema() + "'" +
            ", objectModel='" + getObjectModel() + "'" +
            ", expiredTime='" + getExpiredTime() + "'" +
            ", isDone='" + getIsDone() + "'" +
            ", timeDone='" + getTimeDone() + "'" +
            ", mappingInfo='" + getMappingInfo() + "'" +
            ", sapMapping='" + getSapMapping() + "'" +
            ", dataRoomPath='" + getDataRoomPath() + "'" +
            ", dataRoomId='" + getDataRoomId() + "'" +
            ", dataRoomDriveId='" + getDataRoomDriveId() + "'" +
            ", contractNumber='" + getContractNumber() + "'" +
            ", resultSyncContract='" + getResultSyncContract() + "'" +
            ", contractExpireTime='" + getContractExpireTime() + "'" +
            ", plotOfLandNumber='" + getPlotOfLandNumber() + "'" +
            ", isSyncSap='" + getIsSyncSap() + "'" +
            ", createdName='" + getCreatedName() + "'" +
            ", createdOrgName='" + getCreatedOrgName() + "'" +
            ", createdRankName='" + getCreatedRankName() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", modifiedName='" + getModifiedName() + "'" +
            ", modifiedDate='" + getModifiedDate() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", isDelete='" + getIsDelete() + "'" +
            ", tennantCode='" + getTennantCode() + "'" +
            ", tennantName='" + getTennantName() + "'" +
            ", request=" + getRequest() +
            ", status=" + getStatus() +
            ", tennant=" + getTennant() +
            ", requestType=" + getRequestType() +
            ", requestGroup=" + getRequestGroup() +
            ", organization=" + getOrganization() +
            ", created=" + getCreated() +
            ", modified=" + getModified() +
            ", subStatus=" + getSubStatus() +
            ", reqDataConcerned=" + getReqDataConcerned() +
            ", approver=" + getApprover() +
            ", revoker=" + getRevoker() +
            ", userInfos=" + getUserInfos() +
            "}";
    }
}
