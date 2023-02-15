package com.vsm.business.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Examine.
 */
@Entity
@Table(name = "examine")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "examine")
public class Examine implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "content")
    private String content;

    @Column(name = "send_name")
    private String sendName;

    @Column(name = "organization_name")
    private String organizationName;

    @Column(name = "rank_name")
    private String rankName;

    @Column(name = "send_date")
    private Instant sendDate;

    @Column(name = "receive_name")
    private String receiveName;

    @Column(name = "receive_date")
    private Instant receiveDate;

    @Column(name = "attach_file_list")
    private String attachFileList;

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "nextStep",
            "processData",
            "requestData",
            "tennant",
            "created",
            "modified",
            "stepInProcess",
            "rank",
            "mailTemplate",
            "mailTemplateCustomer",
            "userInfos",
            "previousStep",
            "reqdataProcessHis",
            "examines",
            "consults",
            "attachmentInSteps",
            "requestRecalls",
            "transferHandles",
            "resultOfSteps",
        },
        allowSetters = true
    )
    private StepData stepData;

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
    private UserInfo sender;

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
    private UserInfo receiver;

    @OneToMany(mappedBy = "examine", orphanRemoval = true, cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "sender", "replier", "examine" }, allowSetters = true)
    private Set<ExamineReply> examineReplies = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Examine id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return this.content;
    }

    public Examine content(String content) {
        this.setContent(content);
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSendName() {
        return this.sendName;
    }

    public Examine sendName(String sendName) {
        this.setSendName(sendName);
        return this;
    }

    public void setSendName(String sendName) {
        this.sendName = sendName;
    }

    public String getOrganizationName() {
        return this.organizationName;
    }

    public Examine organizationName(String organizationName) {
        this.setOrganizationName(organizationName);
        return this;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getRankName() {
        return this.rankName;
    }

    public Examine rankName(String rankName) {
        this.setRankName(rankName);
        return this;
    }

    public void setRankName(String rankName) {
        this.rankName = rankName;
    }

    public Instant getSendDate() {
        return this.sendDate;
    }

    public Examine sendDate(Instant sendDate) {
        this.setSendDate(sendDate);
        return this;
    }

    public void setSendDate(Instant sendDate) {
        this.sendDate = sendDate;
    }

    public String getReceiveName() {
        return this.receiveName;
    }

    public Examine receiveName(String receiveName) {
        this.setReceiveName(receiveName);
        return this;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    public Instant getReceiveDate() {
        return this.receiveDate;
    }

    public Examine receiveDate(Instant receiveDate) {
        this.setReceiveDate(receiveDate);
        return this;
    }

    public void setReceiveDate(Instant receiveDate) {
        this.receiveDate = receiveDate;
    }

    public String getAttachFileList() {
        return this.attachFileList;
    }

    public Examine attachFileList(String attachFileList) {
        this.setAttachFileList(attachFileList);
        return this;
    }

    public void setAttachFileList(String attachFileList) {
        this.attachFileList = attachFileList;
    }

    public StepData getStepData() {
        return this.stepData;
    }

    public void setStepData(StepData stepData) {
        this.stepData = stepData;
    }

    public Examine stepData(StepData stepData) {
        this.setStepData(stepData);
        return this;
    }

    public UserInfo getSender() {
        return this.sender;
    }

    public void setSender(UserInfo userInfo) {
        this.sender = userInfo;
    }

    public Examine sender(UserInfo userInfo) {
        this.setSender(userInfo);
        return this;
    }

    public UserInfo getReceiver() {
        return this.receiver;
    }

    public void setReceiver(UserInfo userInfo) {
        this.receiver = userInfo;
    }

    public Examine receiver(UserInfo userInfo) {
        this.setReceiver(userInfo);
        return this;
    }

    public Set<ExamineReply> getExamineReplies() {
        return this.examineReplies;
    }

    public void setExamineReplies(Set<ExamineReply> examineReplies) {
        if (this.examineReplies != null) {
            this.examineReplies.forEach(i -> i.setExamine(null));
        }
        if (examineReplies != null) {
            examineReplies.forEach(i -> i.setExamine(this));
        }
        this.examineReplies = examineReplies;
    }

    public Examine examineReplies(Set<ExamineReply> examineReplies) {
        this.setExamineReplies(examineReplies);
        return this;
    }

    public Examine addExamineReply(ExamineReply examineReply) {
        this.examineReplies.add(examineReply);
        examineReply.setExamine(this);
        return this;
    }

    public Examine removeExamineReply(ExamineReply examineReply) {
        this.examineReplies.remove(examineReply);
        examineReply.setExamine(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Examine)) {
            return false;
        }
        return id != null && id.equals(((Examine) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Examine{" +
            "id=" + getId() +
            ", content='" + getContent() + "'" +
            ", sendName='" + getSendName() + "'" +
            ", organizationName='" + getOrganizationName() + "'" +
            ", rankName='" + getRankName() + "'" +
            ", sendDate='" + getSendDate() + "'" +
            ", receiveName='" + getReceiveName() + "'" +
            ", receiveDate='" + getReceiveDate() + "'" +
            ", attachFileList='" + getAttachFileList() + "'" +
            "}";
    }
}
