package com.vsm.business.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.vsm.business.domain.InformationInExchange} entity.
 */
public class InformationInExchangeDTO implements Serializable {

    private Long id;

    @Lob
    private String content;

    private String sendName;

    private String organizationName;

    private String rankName;

    private Instant sendDate;

    private Long replyOfId;

    private String informationType;

    private String isDelete;

    private UserInfoDTO sender;

    private RequestDataDTO requestData;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSendName() {
        return sendName;
    }

    public void setSendName(String sendName) {
        this.sendName = sendName;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getRankName() {
        return rankName;
    }

    public void setRankName(String rankName) {
        this.rankName = rankName;
    }

    public Instant getSendDate() {
        return sendDate;
    }

    public void setSendDate(Instant sendDate) {
        this.sendDate = sendDate;
    }

    public Long getReplyOfId() {
        return replyOfId;
    }

    public void setReplyOfId(Long replyOfId) {
        this.replyOfId = replyOfId;
    }

    public String getInformationType() {
        return informationType;
    }

    public void setInformationType(String informationType) {
        this.informationType = informationType;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    public UserInfoDTO getSender() {
        return sender;
    }

    public void setSender(UserInfoDTO sender) {
        this.sender = sender;
    }

    public RequestDataDTO getRequestData() {
        return requestData;
    }

    public void setRequestData(RequestDataDTO requestData) {
        this.requestData = requestData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InformationInExchangeDTO)) {
            return false;
        }

        InformationInExchangeDTO informationInExchangeDTO = (InformationInExchangeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, informationInExchangeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InformationInExchangeDTO{" +
            "id=" + getId() +
            ", content='" + getContent() + "'" +
            ", sendName='" + getSendName() + "'" +
            ", organizationName='" + getOrganizationName() + "'" +
            ", rankName='" + getRankName() + "'" +
            ", sendDate='" + getSendDate() + "'" +
            ", replyOfId=" + getReplyOfId() +
            ", informationType='" + getInformationType() + "'" +
            ", isDelete='" + getIsDelete() + "'" +
            ", sender=" + getSender() +
            ", requestData=" + getRequestData() +
            "}";
    }
}
