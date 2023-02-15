package com.vsm.business.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ChangeFileHistory.
 */
@Entity
@Table(name = "change_file_history")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "changefilehistory")
public class ChangeFileHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "change_name")
    private String changeName;

    @Column(name = "organization_name")
    private String organizationName;

    @Column(name = "rank_name")
    private String rankName;

    @Column(name = "comment")
    private String comment;

    @Column(name = "change_date")
    private Instant changeDate;

    @ManyToOne
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
    private AttachmentFile attachmentFile;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ChangeFileHistory id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChangeName() {
        return this.changeName;
    }

    public ChangeFileHistory changeName(String changeName) {
        this.setChangeName(changeName);
        return this;
    }

    public void setChangeName(String changeName) {
        this.changeName = changeName;
    }

    public String getOrganizationName() {
        return this.organizationName;
    }

    public ChangeFileHistory organizationName(String organizationName) {
        this.setOrganizationName(organizationName);
        return this;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getRankName() {
        return this.rankName;
    }

    public ChangeFileHistory rankName(String rankName) {
        this.setRankName(rankName);
        return this;
    }

    public void setRankName(String rankName) {
        this.rankName = rankName;
    }

    public String getComment() {
        return this.comment;
    }

    public ChangeFileHistory comment(String comment) {
        this.setComment(comment);
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Instant getChangeDate() {
        return this.changeDate;
    }

    public ChangeFileHistory changeDate(Instant changeDate) {
        this.setChangeDate(changeDate);
        return this;
    }

    public void setChangeDate(Instant changeDate) {
        this.changeDate = changeDate;
    }

    public AttachmentFile getAttachmentFile() {
        return this.attachmentFile;
    }

    public void setAttachmentFile(AttachmentFile attachmentFile) {
        this.attachmentFile = attachmentFile;
    }

    public ChangeFileHistory attachmentFile(AttachmentFile attachmentFile) {
        this.setAttachmentFile(attachmentFile);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ChangeFileHistory)) {
            return false;
        }
        return id != null && id.equals(((ChangeFileHistory) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ChangeFileHistory{" +
            "id=" + getId() +
            ", changeName='" + getChangeName() + "'" +
            ", organizationName='" + getOrganizationName() + "'" +
            ", rankName='" + getRankName() + "'" +
            ", comment='" + getComment() + "'" +
            ", changeDate='" + getChangeDate() + "'" +
            "}";
    }
}
