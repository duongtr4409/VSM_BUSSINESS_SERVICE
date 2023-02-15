package com.vsm.business.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.vsm.business.domain.MailLog} entity.
 */
public class MailLogDTO implements Serializable {

    private Long id;

    private String mailTitle;

    private String mailContent;

    private String description;

    private String receiveEmail;

    private String ccEmail;

    private String bccEmail;

    private Instant sendDate;

    private String result;

    private Boolean isSucess;

    private Long sendCount;

    private Long sync;

    private Instant createdDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMailTitle() {
        return mailTitle;
    }

    public void setMailTitle(String mailTitle) {
        this.mailTitle = mailTitle;
    }

    public String getMailContent() {
        return mailContent;
    }

    public void setMailContent(String mailContent) {
        this.mailContent = mailContent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReceiveEmail() {
        return receiveEmail;
    }

    public void setReceiveEmail(String receiveEmail) {
        this.receiveEmail = receiveEmail;
    }

    public String getCcEmail() {
        return ccEmail;
    }

    public void setCcEmail(String ccEmail) {
        this.ccEmail = ccEmail;
    }

    public String getBccEmail() {
        return bccEmail;
    }

    public void setBccEmail(String bccEmail) {
        this.bccEmail = bccEmail;
    }

    public Instant getSendDate() {
        return sendDate;
    }

    public void setSendDate(Instant sendDate) {
        this.sendDate = sendDate;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Boolean getIsSucess() {
        return isSucess;
    }

    public void setIsSucess(Boolean isSucess) {
        this.isSucess = isSucess;
    }

    public Long getSendCount() {
        return sendCount;
    }

    public void setSendCount(Long sendCount) {
        this.sendCount = sendCount;
    }

    public Long getSync() {
        return sync;
    }

    public void setSync(Long sync) {
        this.sync = sync;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MailLogDTO)) {
            return false;
        }

        MailLogDTO mailLogDTO = (MailLogDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, mailLogDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MailLogDTO{" +
            "id=" + getId() +
            ", mailTitle='" + getMailTitle() + "'" +
            ", mailContent='" + getMailContent() + "'" +
            ", description='" + getDescription() + "'" +
            ", receiveEmail='" + getReceiveEmail() + "'" +
            ", ccEmail='" + getCcEmail() + "'" +
            ", bccEmail='" + getBccEmail() + "'" +
            ", sendDate='" + getSendDate() + "'" +
            ", result='" + getResult() + "'" +
            ", isSucess='" + getIsSucess() + "'" +
            ", sendCount=" + getSendCount() +
            ", sync=" + getSync() +
            ", createdDate='" + getCreatedDate() + "'" +
            "}";
    }
}
