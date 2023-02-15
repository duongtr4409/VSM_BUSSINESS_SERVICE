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
 * A Consult.
 */
@Entity
@Table(name = "consult")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "consult")
public class Consult implements Serializable {

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

    @OneToMany(mappedBy = "consult", orphanRemoval = true, cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "sender", "replier", "consult" }, allowSetters = true)
    private Set<ConsultReply> consultReplies = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Consult id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return this.content;
    }

    public Consult content(String content) {
        this.setContent(content);
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSendName() {
        return this.sendName;
    }

    public Consult sendName(String sendName) {
        this.setSendName(sendName);
        return this;
    }

    public void setSendName(String sendName) {
        this.sendName = sendName;
    }

    public String getOrganizationName() {
        return this.organizationName;
    }

    public Consult organizationName(String organizationName) {
        this.setOrganizationName(organizationName);
        return this;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getRankName() {
        return this.rankName;
    }

    public Consult rankName(String rankName) {
        this.setRankName(rankName);
        return this;
    }

    public void setRankName(String rankName) {
        this.rankName = rankName;
    }

    public Instant getSendDate() {
        return this.sendDate;
    }

    public Consult sendDate(Instant sendDate) {
        this.setSendDate(sendDate);
        return this;
    }

    public void setSendDate(Instant sendDate) {
        this.sendDate = sendDate;
    }

    public String getReceiveName() {
        return this.receiveName;
    }

    public Consult receiveName(String receiveName) {
        this.setReceiveName(receiveName);
        return this;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    public Instant getReceiveDate() {
        return this.receiveDate;
    }

    public Consult receiveDate(Instant receiveDate) {
        this.setReceiveDate(receiveDate);
        return this;
    }

    public void setReceiveDate(Instant receiveDate) {
        this.receiveDate = receiveDate;
    }

    public String getAttachFileList() {
        return this.attachFileList;
    }

    public Consult attachFileList(String attachFileList) {
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

    public Consult stepData(StepData stepData) {
        this.setStepData(stepData);
        return this;
    }

    public UserInfo getSender() {
        return this.sender;
    }

    public void setSender(UserInfo userInfo) {
        this.sender = userInfo;
    }

    public Consult sender(UserInfo userInfo) {
        this.setSender(userInfo);
        return this;
    }

    public UserInfo getReceiver() {
        return this.receiver;
    }

    public void setReceiver(UserInfo userInfo) {
        this.receiver = userInfo;
    }

    public Consult receiver(UserInfo userInfo) {
        this.setReceiver(userInfo);
        return this;
    }

    public Set<ConsultReply> getConsultReplies() {
        return this.consultReplies;
    }

    public void setConsultReplies(Set<ConsultReply> consultReplies) {
        if (this.consultReplies != null) {
            this.consultReplies.forEach(i -> i.setConsult(null));
        }
        if (consultReplies != null) {
            consultReplies.forEach(i -> i.setConsult(this));
        }
        this.consultReplies = consultReplies;
    }

    public Consult consultReplies(Set<ConsultReply> consultReplies) {
        this.setConsultReplies(consultReplies);
        return this;
    }

    public Consult addConsultReply(ConsultReply consultReply) {
        this.consultReplies.add(consultReply);
        consultReply.setConsult(this);
        return this;
    }

    public Consult removeConsultReply(ConsultReply consultReply) {
        this.consultReplies.remove(consultReply);
        consultReply.setConsult(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Consult)) {
            return false;
        }
        return id != null && id.equals(((Consult) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Consult{" +
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
