package com.vsm.business.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A OfficialDispatchHis.
 */
@Entity
@Table(name = "official_dispatch_his")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "officialdispatchhis")
public class OfficialDispatchHis implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "official_dispatch_name")
    private String officialDispatchName;

    @Column(name = "official_dispatch_code")
    private String officialDispatchCode;

    @Column(name = "official_dispatch_number")
    private String officialDispatchNumber;

    @Column(name = "compendium")
    private String compendium;

    @Column(name = "official_dispatch_type_name")
    private String officialDispatchTypeName;

    @Column(name = "official_dispatch_type_code")
    private String officialDispatchTypeCode;

    @Column(name = "official_dispatch_status_name")
    private String officialDispatchStatusName;

    @Column(name = "official_dispatch_status_code")
    private String officialDispatchStatusCode;

    @Column(name = "release_org_name")
    private String releaseOrgName;

    @Column(name = "release_org_avatar")
    private String releaseOrgAvatar;

    @Column(name = "release_date")
    private Instant releaseDate;

    @Column(name = "compose_org_name")
    private String composeOrgName;

    @Column(name = "compose_org_avatar")
    private String composeOrgAvatar;

    @Column(name = "place_send_name")
    private String placeSendName;

    @Column(name = "place_send_code")
    private String placeSendCode;

    @Column(name = "place_receive_name")
    private String placeReceiveName;

    @Column(name = "place_receive_code")
    private String placeReceiveCode;

    @Column(name = "executor_name")
    private String executorName;

    @Column(name = "executor_org_name")
    private String executorOrgName;

    @Column(name = "executor_rank_name")
    private String executorRankName;

    @Column(name = "action")
    private String action;

    @Column(name = "content")
    private String content;

    @Column(name = "description")
    private String description;

    @Column(name = "created_name")
    private String createdName;

    @Column(name = "created_org_name")
    private String createdOrgName;

    @Column(name = "created_rank_name")
    private String createdRankName;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "modified_name")
    private String modifiedName;

    @Column(name = "modified_date")
    private Instant modifiedDate;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "is_delete")
    private Boolean isDelete;

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "dispatchBook",
            "releaseOrg",
            "composeOrg",
            "ownerOrg",
            "signer",
            "officialDispatchType",
            "documentType",
            "priorityLevel",
            "securityLevel",
            "officialDispatchStatus",
            "creater",
            "modifier",
            "outOrganization",
            "offDispatchUserReads",
            "attachmentFiles",
            "officialDispatchHis",
            "stepProcessDocs",
        },
        allowSetters = true
    )
    private OfficialDispatch officialDispatch;

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "leader",
            "tennant",
            "created",
            "modified",
            "roles",
            "ranks",
            "organizations",
            "userInSteps",
            "createdFields",
            "modifiedFields",
            "createdForms",
            "modifiedForms",
            "createdFieldInForms",
            "modifiedFieldInForms",
            "createdRanks",
            "modifiedRanks",
            "createdRoles",
            "modifiedRoles",
            "createdUserInfos",
            "modifiedUserInfos",
            "createdUserInSteps",
            "modifiedUserInSteps",
            "createdStepInProcesses",
            "modifiedStepInProcesses",
            "createdSteps",
            "modifiedSteps",
            "createdProcessInfos",
            "modifiedProcessInfos",
            "createdTemplateForms",
            "modifiedTemplateForms",
            "createdRequests",
            "modifiedRequests",
            "createdRequestTypes",
            "modifiedRequestTypes",
            "createdRequestGroups",
            "modifiedRequestGroups",
            "createdProcessDatas",
            "modifiedProcessDatas",
            "createdStepDatas",
            "modifiedStepDatas",
            "createdRequestDatas",
            "modifiedRequestDatas",
            "approvedRequestDatas",
            "revokedRequestDatas",
            "createdStatuses",
            "modifiedStatuses",
            "createdFormDatas",
            "modifiedFormDatas",
            "createdFieldDatas",
            "modifiedFieldDatas",
            "createdAttachmentFiles",
            "modifiedAttachmentFiles",
            "createdFileTypes",
            "modifiedFileTypes",
            "createdReqdataChangeHis",
            "modifiedReqdataChangeHis",
            "createdCategoryDatas",
            "modifiedCategoryDatas",
            "createdCategoryGroups",
            "modifiedCategoryGroups",
            "signatureInfomations",
            "attachmentPermisitions",
            "createdAttachmentPermisions",
            "modifiedAttachmentPermisions",
            "signData",
            "requestData",
            "stepData",
            "userGroups",
            "offDispatchUserReads",
            "receiversHandles",
        },
        allowSetters = true
    )
    private UserInfo creater;

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "leader",
            "tennant",
            "created",
            "modified",
            "roles",
            "ranks",
            "organizations",
            "userInSteps",
            "createdFields",
            "modifiedFields",
            "createdForms",
            "modifiedForms",
            "createdFieldInForms",
            "modifiedFieldInForms",
            "createdRanks",
            "modifiedRanks",
            "createdRoles",
            "modifiedRoles",
            "createdUserInfos",
            "modifiedUserInfos",
            "createdUserInSteps",
            "modifiedUserInSteps",
            "createdStepInProcesses",
            "modifiedStepInProcesses",
            "createdSteps",
            "modifiedSteps",
            "createdProcessInfos",
            "modifiedProcessInfos",
            "createdTemplateForms",
            "modifiedTemplateForms",
            "createdRequests",
            "modifiedRequests",
            "createdRequestTypes",
            "modifiedRequestTypes",
            "createdRequestGroups",
            "modifiedRequestGroups",
            "createdProcessDatas",
            "modifiedProcessDatas",
            "createdStepDatas",
            "modifiedStepDatas",
            "createdRequestDatas",
            "modifiedRequestDatas",
            "approvedRequestDatas",
            "revokedRequestDatas",
            "createdStatuses",
            "modifiedStatuses",
            "createdFormDatas",
            "modifiedFormDatas",
            "createdFieldDatas",
            "modifiedFieldDatas",
            "createdAttachmentFiles",
            "modifiedAttachmentFiles",
            "createdFileTypes",
            "modifiedFileTypes",
            "createdReqdataChangeHis",
            "modifiedReqdataChangeHis",
            "createdCategoryDatas",
            "modifiedCategoryDatas",
            "createdCategoryGroups",
            "modifiedCategoryGroups",
            "signatureInfomations",
            "attachmentPermisitions",
            "createdAttachmentPermisions",
            "modifiedAttachmentPermisions",
            "signData",
            "requestData",
            "stepData",
            "userGroups",
            "offDispatchUserReads",
            "receiversHandles",
        },
        allowSetters = true
    )
    private UserInfo modifier;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public OfficialDispatchHis id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOfficialDispatchName() {
        return this.officialDispatchName;
    }

    public OfficialDispatchHis officialDispatchName(String officialDispatchName) {
        this.setOfficialDispatchName(officialDispatchName);
        return this;
    }

    public void setOfficialDispatchName(String officialDispatchName) {
        this.officialDispatchName = officialDispatchName;
    }

    public String getOfficialDispatchCode() {
        return this.officialDispatchCode;
    }

    public OfficialDispatchHis officialDispatchCode(String officialDispatchCode) {
        this.setOfficialDispatchCode(officialDispatchCode);
        return this;
    }

    public void setOfficialDispatchCode(String officialDispatchCode) {
        this.officialDispatchCode = officialDispatchCode;
    }

    public String getOfficialDispatchNumber() {
        return this.officialDispatchNumber;
    }

    public OfficialDispatchHis officialDispatchNumber(String officialDispatchNumber) {
        this.setOfficialDispatchNumber(officialDispatchNumber);
        return this;
    }

    public void setOfficialDispatchNumber(String officialDispatchNumber) {
        this.officialDispatchNumber = officialDispatchNumber;
    }

    public String getCompendium() {
        return this.compendium;
    }

    public OfficialDispatchHis compendium(String compendium) {
        this.setCompendium(compendium);
        return this;
    }

    public void setCompendium(String compendium) {
        this.compendium = compendium;
    }

    public String getOfficialDispatchTypeName() {
        return this.officialDispatchTypeName;
    }

    public OfficialDispatchHis officialDispatchTypeName(String officialDispatchTypeName) {
        this.setOfficialDispatchTypeName(officialDispatchTypeName);
        return this;
    }

    public void setOfficialDispatchTypeName(String officialDispatchTypeName) {
        this.officialDispatchTypeName = officialDispatchTypeName;
    }

    public String getOfficialDispatchTypeCode() {
        return this.officialDispatchTypeCode;
    }

    public OfficialDispatchHis officialDispatchTypeCode(String officialDispatchTypeCode) {
        this.setOfficialDispatchTypeCode(officialDispatchTypeCode);
        return this;
    }

    public void setOfficialDispatchTypeCode(String officialDispatchTypeCode) {
        this.officialDispatchTypeCode = officialDispatchTypeCode;
    }

    public String getOfficialDispatchStatusName() {
        return this.officialDispatchStatusName;
    }

    public OfficialDispatchHis officialDispatchStatusName(String officialDispatchStatusName) {
        this.setOfficialDispatchStatusName(officialDispatchStatusName);
        return this;
    }

    public void setOfficialDispatchStatusName(String officialDispatchStatusName) {
        this.officialDispatchStatusName = officialDispatchStatusName;
    }

    public String getOfficialDispatchStatusCode() {
        return this.officialDispatchStatusCode;
    }

    public OfficialDispatchHis officialDispatchStatusCode(String officialDispatchStatusCode) {
        this.setOfficialDispatchStatusCode(officialDispatchStatusCode);
        return this;
    }

    public void setOfficialDispatchStatusCode(String officialDispatchStatusCode) {
        this.officialDispatchStatusCode = officialDispatchStatusCode;
    }

    public String getReleaseOrgName() {
        return this.releaseOrgName;
    }

    public OfficialDispatchHis releaseOrgName(String releaseOrgName) {
        this.setReleaseOrgName(releaseOrgName);
        return this;
    }

    public void setReleaseOrgName(String releaseOrgName) {
        this.releaseOrgName = releaseOrgName;
    }

    public String getReleaseOrgAvatar() {
        return this.releaseOrgAvatar;
    }

    public OfficialDispatchHis releaseOrgAvatar(String releaseOrgAvatar) {
        this.setReleaseOrgAvatar(releaseOrgAvatar);
        return this;
    }

    public void setReleaseOrgAvatar(String releaseOrgAvatar) {
        this.releaseOrgAvatar = releaseOrgAvatar;
    }

    public Instant getReleaseDate() {
        return this.releaseDate;
    }

    public OfficialDispatchHis releaseDate(Instant releaseDate) {
        this.setReleaseDate(releaseDate);
        return this;
    }

    public void setReleaseDate(Instant releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getComposeOrgName() {
        return this.composeOrgName;
    }

    public OfficialDispatchHis composeOrgName(String composeOrgName) {
        this.setComposeOrgName(composeOrgName);
        return this;
    }

    public void setComposeOrgName(String composeOrgName) {
        this.composeOrgName = composeOrgName;
    }

    public String getComposeOrgAvatar() {
        return this.composeOrgAvatar;
    }

    public OfficialDispatchHis composeOrgAvatar(String composeOrgAvatar) {
        this.setComposeOrgAvatar(composeOrgAvatar);
        return this;
    }

    public void setComposeOrgAvatar(String composeOrgAvatar) {
        this.composeOrgAvatar = composeOrgAvatar;
    }

    public String getPlaceSendName() {
        return this.placeSendName;
    }

    public OfficialDispatchHis placeSendName(String placeSendName) {
        this.setPlaceSendName(placeSendName);
        return this;
    }

    public void setPlaceSendName(String placeSendName) {
        this.placeSendName = placeSendName;
    }

    public String getPlaceSendCode() {
        return this.placeSendCode;
    }

    public OfficialDispatchHis placeSendCode(String placeSendCode) {
        this.setPlaceSendCode(placeSendCode);
        return this;
    }

    public void setPlaceSendCode(String placeSendCode) {
        this.placeSendCode = placeSendCode;
    }

    public String getPlaceReceiveName() {
        return this.placeReceiveName;
    }

    public OfficialDispatchHis placeReceiveName(String placeReceiveName) {
        this.setPlaceReceiveName(placeReceiveName);
        return this;
    }

    public void setPlaceReceiveName(String placeReceiveName) {
        this.placeReceiveName = placeReceiveName;
    }

    public String getPlaceReceiveCode() {
        return this.placeReceiveCode;
    }

    public OfficialDispatchHis placeReceiveCode(String placeReceiveCode) {
        this.setPlaceReceiveCode(placeReceiveCode);
        return this;
    }

    public void setPlaceReceiveCode(String placeReceiveCode) {
        this.placeReceiveCode = placeReceiveCode;
    }

    public String getExecutorName() {
        return this.executorName;
    }

    public OfficialDispatchHis executorName(String executorName) {
        this.setExecutorName(executorName);
        return this;
    }

    public void setExecutorName(String executorName) {
        this.executorName = executorName;
    }

    public String getExecutorOrgName() {
        return this.executorOrgName;
    }

    public OfficialDispatchHis executorOrgName(String executorOrgName) {
        this.setExecutorOrgName(executorOrgName);
        return this;
    }

    public void setExecutorOrgName(String executorOrgName) {
        this.executorOrgName = executorOrgName;
    }

    public String getExecutorRankName() {
        return this.executorRankName;
    }

    public OfficialDispatchHis executorRankName(String executorRankName) {
        this.setExecutorRankName(executorRankName);
        return this;
    }

    public void setExecutorRankName(String executorRankName) {
        this.executorRankName = executorRankName;
    }

    public String getAction() {
        return this.action;
    }

    public OfficialDispatchHis action(String action) {
        this.setAction(action);
        return this;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getContent() {
        return this.content;
    }

    public OfficialDispatchHis content(String content) {
        this.setContent(content);
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDescription() {
        return this.description;
    }

    public OfficialDispatchHis description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedName() {
        return this.createdName;
    }

    public OfficialDispatchHis createdName(String createdName) {
        this.setCreatedName(createdName);
        return this;
    }

    public void setCreatedName(String createdName) {
        this.createdName = createdName;
    }

    public String getCreatedOrgName() {
        return this.createdOrgName;
    }

    public OfficialDispatchHis createdOrgName(String createdOrgName) {
        this.setCreatedOrgName(createdOrgName);
        return this;
    }

    public void setCreatedOrgName(String createdOrgName) {
        this.createdOrgName = createdOrgName;
    }

    public String getCreatedRankName() {
        return this.createdRankName;
    }

    public OfficialDispatchHis createdRankName(String createdRankName) {
        this.setCreatedRankName(createdRankName);
        return this;
    }

    public void setCreatedRankName(String createdRankName) {
        this.createdRankName = createdRankName;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public OfficialDispatchHis createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedName() {
        return this.modifiedName;
    }

    public OfficialDispatchHis modifiedName(String modifiedName) {
        this.setModifiedName(modifiedName);
        return this;
    }

    public void setModifiedName(String modifiedName) {
        this.modifiedName = modifiedName;
    }

    public Instant getModifiedDate() {
        return this.modifiedDate;
    }

    public OfficialDispatchHis modifiedDate(Instant modifiedDate) {
        this.setModifiedDate(modifiedDate);
        return this;
    }

    public void setModifiedDate(Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public OfficialDispatchHis isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getIsDelete() {
        return this.isDelete;
    }

    public OfficialDispatchHis isDelete(Boolean isDelete) {
        this.setIsDelete(isDelete);
        return this;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public OfficialDispatch getOfficialDispatch() {
        return this.officialDispatch;
    }

    public void setOfficialDispatch(OfficialDispatch officialDispatch) {
        this.officialDispatch = officialDispatch;
    }

    public OfficialDispatchHis officialDispatch(OfficialDispatch officialDispatch) {
        this.setOfficialDispatch(officialDispatch);
        return this;
    }

    public UserInfo getCreater() {
        return this.creater;
    }

    public void setCreater(UserInfo userInfo) {
        this.creater = userInfo;
    }

    public OfficialDispatchHis creater(UserInfo userInfo) {
        this.setCreater(userInfo);
        return this;
    }

    public UserInfo getModifier() {
        return this.modifier;
    }

    public void setModifier(UserInfo userInfo) {
        this.modifier = userInfo;
    }

    public OfficialDispatchHis modifier(UserInfo userInfo) {
        this.setModifier(userInfo);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OfficialDispatchHis)) {
            return false;
        }
        return id != null && id.equals(((OfficialDispatchHis) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OfficialDispatchHis{" +
            "id=" + getId() +
            ", officialDispatchName='" + getOfficialDispatchName() + "'" +
            ", officialDispatchCode='" + getOfficialDispatchCode() + "'" +
            ", officialDispatchNumber='" + getOfficialDispatchNumber() + "'" +
            ", compendium='" + getCompendium() + "'" +
            ", officialDispatchTypeName='" + getOfficialDispatchTypeName() + "'" +
            ", officialDispatchTypeCode='" + getOfficialDispatchTypeCode() + "'" +
            ", officialDispatchStatusName='" + getOfficialDispatchStatusName() + "'" +
            ", officialDispatchStatusCode='" + getOfficialDispatchStatusCode() + "'" +
            ", releaseOrgName='" + getReleaseOrgName() + "'" +
            ", releaseOrgAvatar='" + getReleaseOrgAvatar() + "'" +
            ", releaseDate='" + getReleaseDate() + "'" +
            ", composeOrgName='" + getComposeOrgName() + "'" +
            ", composeOrgAvatar='" + getComposeOrgAvatar() + "'" +
            ", placeSendName='" + getPlaceSendName() + "'" +
            ", placeSendCode='" + getPlaceSendCode() + "'" +
            ", placeReceiveName='" + getPlaceReceiveName() + "'" +
            ", placeReceiveCode='" + getPlaceReceiveCode() + "'" +
            ", executorName='" + getExecutorName() + "'" +
            ", executorOrgName='" + getExecutorOrgName() + "'" +
            ", executorRankName='" + getExecutorRankName() + "'" +
            ", action='" + getAction() + "'" +
            ", content='" + getContent() + "'" +
            ", description='" + getDescription() + "'" +
            ", createdName='" + getCreatedName() + "'" +
            ", createdOrgName='" + getCreatedOrgName() + "'" +
            ", createdRankName='" + getCreatedRankName() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", modifiedName='" + getModifiedName() + "'" +
            ", modifiedDate='" + getModifiedDate() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", isDelete='" + getIsDelete() + "'" +
            "}";
    }
}
