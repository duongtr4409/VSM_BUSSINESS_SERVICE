package com.vsm.business.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.vsm.business.domain.Consult} entity.
 */
public class ConsultDTO implements Serializable {

    private Long id;

    private String content;

    private String sendName;

    private String organizationName;

    private String rankName;

    private Instant sendDate;

    private String receiveName;

    private Instant receiveDate;

    private String attachFileList;

    private StepDataDTO stepData;

    private UserInfoDTO sender;

    private UserInfoDTO receiver;

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

    public String getReceiveName() {
        return receiveName;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    public Instant getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(Instant receiveDate) {
        this.receiveDate = receiveDate;
    }

    public String getAttachFileList() {
        return attachFileList;
    }

    public void setAttachFileList(String attachFileList) {
        this.attachFileList = attachFileList;
    }

    public StepDataDTO getStepData() {
        return stepData;
    }

    public void setStepData(StepDataDTO stepData) {
        this.stepData = stepData;
    }

    public UserInfoDTO getSender() {
        return sender;
    }

    public void setSender(UserInfoDTO sender) {
        this.sender = sender;
    }

    public UserInfoDTO getReceiver() {
        return receiver;
    }

    public void setReceiver(UserInfoDTO receiver) {
        this.receiver = receiver;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConsultDTO)) {
            return false;
        }

        ConsultDTO consultDTO = (ConsultDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, consultDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ConsultDTO{" +
            "id=" + getId() +
            ", content='" + getContent() + "'" +
            ", sendName='" + getSendName() + "'" +
            ", organizationName='" + getOrganizationName() + "'" +
            ", rankName='" + getRankName() + "'" +
            ", sendDate='" + getSendDate() + "'" +
            ", receiveName='" + getReceiveName() + "'" +
            ", receiveDate='" + getReceiveDate() + "'" +
            ", attachFileList='" + getAttachFileList() + "'" +
            ", stepData=" + getStepData() +
            ", sender=" + getSender() +
            ", receiver=" + getReceiver() +
            "}";
    }
}
