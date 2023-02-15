package com.vsm.business.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ResultOfStep.
 */
@Entity
@Table(name = "result_of_step")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "resultofstep")
public class ResultOfStep implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "action")
    private String action;

    @Column(name = "action_name")
    private String actionName;

    @Column(name = "excutor_name")
    private String excutorName;

    @Column(name = "excutor_rank_name")
    private String excutorRankName;

    @Column(name = "executor_org_name")
    private String executorOrgName;

    @Column(name = "excute_date")
    private Instant excuteDate;

    @Column(name = "tennant_code")
    private String tennantCode;

    @Column(name = "tennant_name")
    private String tennantName;

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
    private UserInfo excutor;

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
            "fields",
            "forms",
            "fieldInForms",
            "ranks",
            "roles",
            "userInfos",
            "userInSteps",
            "stepInProcesses",
            "steps",
            "processInfos",
            "templateForms",
            "requests",
            "requestTypes",
            "requestGroups",
            "processData",
            "stepData",
            "requestData",
            "statuses",
            "formData",
            "fieldData",
            "attachmentFiles",
            "fileTypes",
            "reqdataChangeHis",
            "categoryData",
            "categoryGroups",
            "resultOfSteps",
        },
        allowSetters = true
    )
    private Tennant tennant;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ResultOfStep id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAction() {
        return this.action;
    }

    public ResultOfStep action(String action) {
        this.setAction(action);
        return this;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getActionName() {
        return this.actionName;
    }

    public ResultOfStep actionName(String actionName) {
        this.setActionName(actionName);
        return this;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getExcutorName() {
        return this.excutorName;
    }

    public ResultOfStep excutorName(String excutorName) {
        this.setExcutorName(excutorName);
        return this;
    }

    public void setExcutorName(String excutorName) {
        this.excutorName = excutorName;
    }

    public String getExcutorRankName() {
        return this.excutorRankName;
    }

    public ResultOfStep excutorRankName(String excutorRankName) {
        this.setExcutorRankName(excutorRankName);
        return this;
    }

    public void setExcutorRankName(String excutorRankName) {
        this.excutorRankName = excutorRankName;
    }

    public String getExecutorOrgName() {
        return this.executorOrgName;
    }

    public ResultOfStep executorOrgName(String executorOrgName) {
        this.setExecutorOrgName(executorOrgName);
        return this;
    }

    public void setExecutorOrgName(String executorOrgName) {
        this.executorOrgName = executorOrgName;
    }

    public Instant getExcuteDate() {
        return this.excuteDate;
    }

    public ResultOfStep excuteDate(Instant excuteDate) {
        this.setExcuteDate(excuteDate);
        return this;
    }

    public void setExcuteDate(Instant excuteDate) {
        this.excuteDate = excuteDate;
    }

    public String getTennantCode() {
        return this.tennantCode;
    }

    public ResultOfStep tennantCode(String tennantCode) {
        this.setTennantCode(tennantCode);
        return this;
    }

    public void setTennantCode(String tennantCode) {
        this.tennantCode = tennantCode;
    }

    public String getTennantName() {
        return this.tennantName;
    }

    public ResultOfStep tennantName(String tennantName) {
        this.setTennantName(tennantName);
        return this;
    }

    public void setTennantName(String tennantName) {
        this.tennantName = tennantName;
    }

    public UserInfo getExcutor() {
        return this.excutor;
    }

    public void setExcutor(UserInfo userInfo) {
        this.excutor = userInfo;
    }

    public ResultOfStep excutor(UserInfo userInfo) {
        this.setExcutor(userInfo);
        return this;
    }

    public StepData getStepData() {
        return this.stepData;
    }

    public void setStepData(StepData stepData) {
        this.stepData = stepData;
    }

    public ResultOfStep stepData(StepData stepData) {
        this.setStepData(stepData);
        return this;
    }

    public Tennant getTennant() {
        return this.tennant;
    }

    public void setTennant(Tennant tennant) {
        this.tennant = tennant;
    }

    public ResultOfStep tennant(Tennant tennant) {
        this.setTennant(tennant);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ResultOfStep)) {
            return false;
        }
        return id != null && id.equals(((ResultOfStep) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ResultOfStep{" +
            "id=" + getId() +
            ", action='" + getAction() + "'" +
            ", actionName='" + getActionName() + "'" +
            ", excutorName='" + getExcutorName() + "'" +
            ", excutorRankName='" + getExcutorRankName() + "'" +
            ", executorOrgName='" + getExecutorOrgName() + "'" +
            ", excuteDate='" + getExcuteDate() + "'" +
            ", tennantCode='" + getTennantCode() + "'" +
            ", tennantName='" + getTennantName() + "'" +
            "}";
    }
}
