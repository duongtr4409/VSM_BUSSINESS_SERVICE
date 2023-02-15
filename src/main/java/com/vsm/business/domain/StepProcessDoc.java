package com.vsm.business.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A StepProcessDoc.
 */
@Entity
@Table(name = "step_process_doc")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "stepprocessdoc")
public class StepProcessDoc implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "processer_name")
    private String processerName;

    @Column(name = "processer_avatar")
    private String processerAvatar;

    @Column(name = "processer_email")
    private String processerEmail;

    @Column(name = "involve_user_text")
    private String involveUserText;

    @Column(name = "content")
    private String content;

    @Column(name = "expire_time")
    private Instant expireTime;

    @Column(name = "transfer_time")
    private Instant transferTime;

    @Column(name = "transfer_name")
    private String transferName;

    @Column(name = "transfer_avatar")
    private String transferAvatar;

    @Column(name = "transfer_email")
    private String transferEmail;

    @Column(name = "vsm_order")
    private Long order;

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

    @OneToMany(mappedBy = "stepProcessDoc")
    @JsonIgnoreProperties(
        value = {
            "fileType",
            "requestData",
            "tennant",
            "created",
            "modified",
            "templateForm",
            "reqdataProcessHis",
            "officialDispatch",
            "stepProcessDoc",
            "mailTemplate",
            "manageStampInfo",
            "attachmentPermisitions",
            "changeFileHistories",
        },
        allowSetters = true
    )
    private Set<AttachmentFile> attachmentFiles = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public StepProcessDoc id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProcesserName() {
        return this.processerName;
    }

    public StepProcessDoc processerName(String processerName) {
        this.setProcesserName(processerName);
        return this;
    }

    public void setProcesserName(String processerName) {
        this.processerName = processerName;
    }

    public String getProcesserAvatar() {
        return this.processerAvatar;
    }

    public StepProcessDoc processerAvatar(String processerAvatar) {
        this.setProcesserAvatar(processerAvatar);
        return this;
    }

    public void setProcesserAvatar(String processerAvatar) {
        this.processerAvatar = processerAvatar;
    }

    public String getProcesserEmail() {
        return this.processerEmail;
    }

    public StepProcessDoc processerEmail(String processerEmail) {
        this.setProcesserEmail(processerEmail);
        return this;
    }

    public void setProcesserEmail(String processerEmail) {
        this.processerEmail = processerEmail;
    }

    public String getInvolveUserText() {
        return this.involveUserText;
    }

    public StepProcessDoc involveUserText(String involveUserText) {
        this.setInvolveUserText(involveUserText);
        return this;
    }

    public void setInvolveUserText(String involveUserText) {
        this.involveUserText = involveUserText;
    }

    public String getContent() {
        return this.content;
    }

    public StepProcessDoc content(String content) {
        this.setContent(content);
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Instant getExpireTime() {
        return this.expireTime;
    }

    public StepProcessDoc expireTime(Instant expireTime) {
        this.setExpireTime(expireTime);
        return this;
    }

    public void setExpireTime(Instant expireTime) {
        this.expireTime = expireTime;
    }

    public Instant getTransferTime() {
        return this.transferTime;
    }

    public StepProcessDoc transferTime(Instant transferTime) {
        this.setTransferTime(transferTime);
        return this;
    }

    public void setTransferTime(Instant transferTime) {
        this.transferTime = transferTime;
    }

    public String getTransferName() {
        return this.transferName;
    }

    public StepProcessDoc transferName(String transferName) {
        this.setTransferName(transferName);
        return this;
    }

    public void setTransferName(String transferName) {
        this.transferName = transferName;
    }

    public String getTransferAvatar() {
        return this.transferAvatar;
    }

    public StepProcessDoc transferAvatar(String transferAvatar) {
        this.setTransferAvatar(transferAvatar);
        return this;
    }

    public void setTransferAvatar(String transferAvatar) {
        this.transferAvatar = transferAvatar;
    }

    public String getTransferEmail() {
        return this.transferEmail;
    }

    public StepProcessDoc transferEmail(String transferEmail) {
        this.setTransferEmail(transferEmail);
        return this;
    }

    public void setTransferEmail(String transferEmail) {
        this.transferEmail = transferEmail;
    }

    public Long getOrder() {
        return this.order;
    }

    public StepProcessDoc order(Long order) {
        this.setOrder(order);
        return this;
    }

    public void setOrder(Long order) {
        this.order = order;
    }

    public String getCreatedName() {
        return this.createdName;
    }

    public StepProcessDoc createdName(String createdName) {
        this.setCreatedName(createdName);
        return this;
    }

    public void setCreatedName(String createdName) {
        this.createdName = createdName;
    }

    public String getCreatedOrgName() {
        return this.createdOrgName;
    }

    public StepProcessDoc createdOrgName(String createdOrgName) {
        this.setCreatedOrgName(createdOrgName);
        return this;
    }

    public void setCreatedOrgName(String createdOrgName) {
        this.createdOrgName = createdOrgName;
    }

    public String getCreatedRankName() {
        return this.createdRankName;
    }

    public StepProcessDoc createdRankName(String createdRankName) {
        this.setCreatedRankName(createdRankName);
        return this;
    }

    public void setCreatedRankName(String createdRankName) {
        this.createdRankName = createdRankName;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public StepProcessDoc createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedName() {
        return this.modifiedName;
    }

    public StepProcessDoc modifiedName(String modifiedName) {
        this.setModifiedName(modifiedName);
        return this;
    }

    public void setModifiedName(String modifiedName) {
        this.modifiedName = modifiedName;
    }

    public Instant getModifiedDate() {
        return this.modifiedDate;
    }

    public StepProcessDoc modifiedDate(Instant modifiedDate) {
        this.setModifiedDate(modifiedDate);
        return this;
    }

    public void setModifiedDate(Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public StepProcessDoc isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getIsDelete() {
        return this.isDelete;
    }

    public StepProcessDoc isDelete(Boolean isDelete) {
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

    public StepProcessDoc officialDispatch(OfficialDispatch officialDispatch) {
        this.setOfficialDispatch(officialDispatch);
        return this;
    }

    public Set<AttachmentFile> getAttachmentFiles() {
        return this.attachmentFiles;
    }

    public void setAttachmentFiles(Set<AttachmentFile> attachmentFiles) {
        if (this.attachmentFiles != null) {
            this.attachmentFiles.forEach(i -> i.setStepProcessDoc(null));
        }
        if (attachmentFiles != null) {
            attachmentFiles.forEach(i -> i.setStepProcessDoc(this));
        }
        this.attachmentFiles = attachmentFiles;
    }

    public StepProcessDoc attachmentFiles(Set<AttachmentFile> attachmentFiles) {
        this.setAttachmentFiles(attachmentFiles);
        return this;
    }

    public StepProcessDoc addAttachmentFile(AttachmentFile attachmentFile) {
        this.attachmentFiles.add(attachmentFile);
        attachmentFile.setStepProcessDoc(this);
        return this;
    }

    public StepProcessDoc removeAttachmentFile(AttachmentFile attachmentFile) {
        this.attachmentFiles.remove(attachmentFile);
        attachmentFile.setStepProcessDoc(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StepProcessDoc)) {
            return false;
        }
        return id != null && id.equals(((StepProcessDoc) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StepProcessDoc{" +
            "id=" + getId() +
            ", processerName='" + getProcesserName() + "'" +
            ", processerAvatar='" + getProcesserAvatar() + "'" +
            ", processerEmail='" + getProcesserEmail() + "'" +
            ", involveUserText='" + getInvolveUserText() + "'" +
            ", content='" + getContent() + "'" +
            ", expireTime='" + getExpireTime() + "'" +
            ", transferTime='" + getTransferTime() + "'" +
            ", transferName='" + getTransferName() + "'" +
            ", transferAvatar='" + getTransferAvatar() + "'" +
            ", transferEmail='" + getTransferEmail() + "'" +
            ", order=" + getOrder() +
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
