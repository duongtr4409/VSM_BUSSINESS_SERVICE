package com.vsm.business.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A MailLog.
 */
@Entity
@Table(name = "mail_log")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "maillog")
public class MailLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "mail_title")
    private String mailTitle;

    @Column(name = "mail_content")
    private String mailContent;

    @Column(name = "description")
    private String description;

    @Column(name = "receive_email")
    private String receiveEmail;

    @Column(name = "cc_email")
    private String ccEmail;

    @Column(name = "bcc_email")
    private String bccEmail;

    @Column(name = "send_date")
    private Instant sendDate;

    @Column(name = "result")
    private String result;

    @Column(name = "is_sucess")
    private Boolean isSucess;

    @Column(name = "send_count")
    private Long sendCount;

    @Column(name = "sync")
    private Long sync;

    @Column(name = "created_date")
    private Instant createdDate;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public MailLog id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMailTitle() {
        return this.mailTitle;
    }

    public MailLog mailTitle(String mailTitle) {
        this.setMailTitle(mailTitle);
        return this;
    }

    public void setMailTitle(String mailTitle) {
        this.mailTitle = mailTitle;
    }

    public String getMailContent() {
        return this.mailContent;
    }

    public MailLog mailContent(String mailContent) {
        this.setMailContent(mailContent);
        return this;
    }

    public void setMailContent(String mailContent) {
        this.mailContent = mailContent;
    }

    public String getDescription() {
        return this.description;
    }

    public MailLog description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReceiveEmail() {
        return this.receiveEmail;
    }

    public MailLog receiveEmail(String receiveEmail) {
        this.setReceiveEmail(receiveEmail);
        return this;
    }

    public void setReceiveEmail(String receiveEmail) {
        this.receiveEmail = receiveEmail;
    }

    public String getCcEmail() {
        return this.ccEmail;
    }

    public MailLog ccEmail(String ccEmail) {
        this.setCcEmail(ccEmail);
        return this;
    }

    public void setCcEmail(String ccEmail) {
        this.ccEmail = ccEmail;
    }

    public String getBccEmail() {
        return this.bccEmail;
    }

    public MailLog bccEmail(String bccEmail) {
        this.setBccEmail(bccEmail);
        return this;
    }

    public void setBccEmail(String bccEmail) {
        this.bccEmail = bccEmail;
    }

    public Instant getSendDate() {
        return this.sendDate;
    }

    public MailLog sendDate(Instant sendDate) {
        this.setSendDate(sendDate);
        return this;
    }

    public void setSendDate(Instant sendDate) {
        this.sendDate = sendDate;
    }

    public String getResult() {
        return this.result;
    }

    public MailLog result(String result) {
        this.setResult(result);
        return this;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Boolean getIsSucess() {
        return this.isSucess;
    }

    public MailLog isSucess(Boolean isSucess) {
        this.setIsSucess(isSucess);
        return this;
    }

    public void setIsSucess(Boolean isSucess) {
        this.isSucess = isSucess;
    }

    public Long getSendCount() {
        return this.sendCount;
    }

    public MailLog sendCount(Long sendCount) {
        this.setSendCount(sendCount);
        return this;
    }

    public void setSendCount(Long sendCount) {
        this.sendCount = sendCount;
    }

    public Long getSync() {
        return this.sync;
    }

    public MailLog sync(Long sync) {
        this.setSync(sync);
        return this;
    }

    public void setSync(Long sync) {
        this.sync = sync;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public MailLog createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MailLog)) {
            return false;
        }
        return id != null && id.equals(((MailLog) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MailLog{" +
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
