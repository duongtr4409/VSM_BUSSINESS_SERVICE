package com.vsm.business.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

/**
 * A InformationInExchange.
 */
@Entity
@Table(name = "information_in_exchange")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "informationinexchange")
public class InformationInExchange implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
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

    @Column(name = "reply_of_id")
    private Long replyOfId;

    @Column(name = "information_type")
    private String informationType;

    @Column(name = "is_delete")
    private String isDelete;

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

    @OneToMany(mappedBy = "informationInExchange")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "tager", "tagged", "requestData", "informationInExchange" }, allowSetters = true)
    private Set<TagInExchange> tagInExchanges = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public InformationInExchange id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return this.content;
    }

    public InformationInExchange content(String content) {
        this.setContent(content);
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSendName() {
        return this.sendName;
    }

    public InformationInExchange sendName(String sendName) {
        this.setSendName(sendName);
        return this;
    }

    public void setSendName(String sendName) {
        this.sendName = sendName;
    }

    public String getOrganizationName() {
        return this.organizationName;
    }

    public InformationInExchange organizationName(String organizationName) {
        this.setOrganizationName(organizationName);
        return this;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getRankName() {
        return this.rankName;
    }

    public InformationInExchange rankName(String rankName) {
        this.setRankName(rankName);
        return this;
    }

    public void setRankName(String rankName) {
        this.rankName = rankName;
    }

    public Instant getSendDate() {
        return this.sendDate;
    }

    public InformationInExchange sendDate(Instant sendDate) {
        this.setSendDate(sendDate);
        return this;
    }

    public void setSendDate(Instant sendDate) {
        this.sendDate = sendDate;
    }

    public Long getReplyOfId() {
        return this.replyOfId;
    }

    public InformationInExchange replyOfId(Long replyOfId) {
        this.setReplyOfId(replyOfId);
        return this;
    }

    public void setReplyOfId(Long replyOfId) {
        this.replyOfId = replyOfId;
    }

    public String getInformationType() {
        return this.informationType;
    }

    public InformationInExchange informationType(String informationType) {
        this.setInformationType(informationType);
        return this;
    }

    public void setInformationType(String informationType) {
        this.informationType = informationType;
    }

    public String getIsDelete() {
        return this.isDelete;
    }

    public InformationInExchange isDelete(String isDelete) {
        this.setIsDelete(isDelete);
        return this;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    public UserInfo getSender() {
        return this.sender;
    }

    public void setSender(UserInfo userInfo) {
        this.sender = userInfo;
    }

    public InformationInExchange sender(UserInfo userInfo) {
        this.setSender(userInfo);
        return this;
    }

    public RequestData getRequestData() {
        return this.requestData;
    }

    public void setRequestData(RequestData requestData) {
        this.requestData = requestData;
    }

    public InformationInExchange requestData(RequestData requestData) {
        this.setRequestData(requestData);
        return this;
    }

    public Set<TagInExchange> getTagInExchanges() {
        return this.tagInExchanges;
    }

    public void setTagInExchanges(Set<TagInExchange> tagInExchanges) {
        if (this.tagInExchanges != null) {
            this.tagInExchanges.forEach(i -> i.setInformationInExchange(null));
        }
        if (tagInExchanges != null) {
            tagInExchanges.forEach(i -> i.setInformationInExchange(this));
        }
        this.tagInExchanges = tagInExchanges;
    }

    public InformationInExchange tagInExchanges(Set<TagInExchange> tagInExchanges) {
        this.setTagInExchanges(tagInExchanges);
        return this;
    }

    public InformationInExchange addTagInExchange(TagInExchange tagInExchange) {
        this.tagInExchanges.add(tagInExchange);
        tagInExchange.setInformationInExchange(this);
        return this;
    }

    public InformationInExchange removeTagInExchange(TagInExchange tagInExchange) {
        this.tagInExchanges.remove(tagInExchange);
        tagInExchange.setInformationInExchange(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InformationInExchange)) {
            return false;
        }
        return id != null && id.equals(((InformationInExchange) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InformationInExchange{" +
            "id=" + getId() +
            ", content='" + getContent() + "'" +
            ", sendName='" + getSendName() + "'" +
            ", organizationName='" + getOrganizationName() + "'" +
            ", rankName='" + getRankName() + "'" +
            ", sendDate='" + getSendDate() + "'" +
            ", replyOfId=" + getReplyOfId() +
            ", informationType='" + getInformationType() + "'" +
            ", isDelete='" + getIsDelete() + "'" +
            "}";
    }
}
