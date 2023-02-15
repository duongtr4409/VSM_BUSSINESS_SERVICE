package com.vsm.business.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;

/**
 * A TagInExchange.
 */
@Entity
@Table(name = "tag_in_exchange")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "taginexchange")
public class TagInExchange implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "tager_name")
    private String tagerName;

    @Column(name = "tager_org")
    private String tagerOrg;

    @Column(name = "tager_rank")
    private String tagerRank;

    @Column(name = "tagged_name")
    private String taggedName;

    @Column(name = "tagged_org")
    private String taggedOrg;

    @Column(name = "tagged_rank")
    private String taggedRank;

    @Column(name = "request_data_code")
    private String requestDataCode;

    @Column(name = "tag_date")
    private Instant tagDate;

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
    private UserInfo tager;

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
    private UserInfo tagged;

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "request",
            "status",
            "tennant",
            "requestType",
            "requestGroup",
            "organization",
            "created",
            "modified",
            "subStatus",
            "reqDataConcerned",
            "approver",
            "revoker",
            "userInfos",
            "formData",
            "attachmentFiles",
            "reqdataProcessHis",
            "reqdataChangeHis",
            "processData",
            "stepData",
            "fieldData",
            "informationInExchanges",
            "tagInExchanges",
            "requestRecalls",
            "oTPS",
            "signData",
            "manageStampInfos",
        },
        allowSetters = true
    )
    private RequestData requestData;

    @ManyToOne
    @JsonIgnoreProperties(value = { "sender", "requestData", "tagInExchanges" }, allowSetters = true)
    private InformationInExchange informationInExchange;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TagInExchange id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTagerName() {
        return this.tagerName;
    }

    public TagInExchange tagerName(String tagerName) {
        this.setTagerName(tagerName);
        return this;
    }

    public void setTagerName(String tagerName) {
        this.tagerName = tagerName;
    }

    public String getTagerOrg() {
        return this.tagerOrg;
    }

    public TagInExchange tagerOrg(String tagerOrg) {
        this.setTagerOrg(tagerOrg);
        return this;
    }

    public void setTagerOrg(String tagerOrg) {
        this.tagerOrg = tagerOrg;
    }

    public String getTagerRank() {
        return this.tagerRank;
    }

    public TagInExchange tagerRank(String tagerRank) {
        this.setTagerRank(tagerRank);
        return this;
    }

    public void setTagerRank(String tagerRank) {
        this.tagerRank = tagerRank;
    }

    public String getTaggedName() {
        return this.taggedName;
    }

    public TagInExchange taggedName(String taggedName) {
        this.setTaggedName(taggedName);
        return this;
    }

    public void setTaggedName(String taggedName) {
        this.taggedName = taggedName;
    }

    public String getTaggedOrg() {
        return this.taggedOrg;
    }

    public TagInExchange taggedOrg(String taggedOrg) {
        this.setTaggedOrg(taggedOrg);
        return this;
    }

    public void setTaggedOrg(String taggedOrg) {
        this.taggedOrg = taggedOrg;
    }

    public String getTaggedRank() {
        return this.taggedRank;
    }

    public TagInExchange taggedRank(String taggedRank) {
        this.setTaggedRank(taggedRank);
        return this;
    }

    public void setTaggedRank(String taggedRank) {
        this.taggedRank = taggedRank;
    }

    public String getRequestDataCode() {
        return this.requestDataCode;
    }

    public TagInExchange requestDataCode(String requestDataCode) {
        this.setRequestDataCode(requestDataCode);
        return this;
    }

    public void setRequestDataCode(String requestDataCode) {
        this.requestDataCode = requestDataCode;
    }

    public Instant getTagDate() {
        return this.tagDate;
    }

    public TagInExchange tagDate(Instant tagDate) {
        this.setTagDate(tagDate);
        return this;
    }

    public void setTagDate(Instant tagDate) {
        this.tagDate = tagDate;
    }

    public UserInfo getTager() {
        return this.tager;
    }

    public void setTager(UserInfo userInfo) {
        this.tager = userInfo;
    }

    public TagInExchange tager(UserInfo userInfo) {
        this.setTager(userInfo);
        return this;
    }

    public UserInfo getTagged() {
        return this.tagged;
    }

    public void setTagged(UserInfo userInfo) {
        this.tagged = userInfo;
    }

    public TagInExchange tagged(UserInfo userInfo) {
        this.setTagged(userInfo);
        return this;
    }

    public RequestData getRequestData() {
        return this.requestData;
    }

    public void setRequestData(RequestData requestData) {
        this.requestData = requestData;
    }

    public TagInExchange requestData(RequestData requestData) {
        this.setRequestData(requestData);
        return this;
    }

    public InformationInExchange getInformationInExchange() {
        return this.informationInExchange;
    }

    public void setInformationInExchange(InformationInExchange informationInExchange) {
        this.informationInExchange = informationInExchange;
    }

    public TagInExchange informationInExchange(InformationInExchange informationInExchange) {
        this.setInformationInExchange(informationInExchange);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TagInExchange)) {
            return false;
        }
        return id != null && id.equals(((TagInExchange) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TagInExchange{" +
            "id=" + getId() +
            ", tagerName='" + getTagerName() + "'" +
            ", tagerOrg='" + getTagerOrg() + "'" +
            ", tagerRank='" + getTagerRank() + "'" +
            ", taggedName='" + getTaggedName() + "'" +
            ", taggedOrg='" + getTaggedOrg() + "'" +
            ", taggedRank='" + getTaggedRank() + "'" +
            ", requestDataCode='" + getRequestDataCode() + "'" +
            ", tagDate='" + getTagDate() + "'" +
            "}";
    }
}
