package com.vsm.business.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.vsm.business.domain.ChangeFileHistory} entity.
 */
public class ChangeFileHistoryDTO implements Serializable {

    private Long id;

    private String changeName;

    private String organizationName;

    private String rankName;

    private String comment;

    private Instant changeDate;

    private AttachmentFileDTO attachmentFile;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChangeName() {
        return changeName;
    }

    public void setChangeName(String changeName) {
        this.changeName = changeName;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Instant getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(Instant changeDate) {
        this.changeDate = changeDate;
    }

    public AttachmentFileDTO getAttachmentFile() {
        return attachmentFile;
    }

    public void setAttachmentFile(AttachmentFileDTO attachmentFile) {
        this.attachmentFile = attachmentFile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ChangeFileHistoryDTO)) {
            return false;
        }

        ChangeFileHistoryDTO changeFileHistoryDTO = (ChangeFileHistoryDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, changeFileHistoryDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ChangeFileHistoryDTO{" +
            "id=" + getId() +
            ", changeName='" + getChangeName() + "'" +
            ", organizationName='" + getOrganizationName() + "'" +
            ", rankName='" + getRankName() + "'" +
            ", comment='" + getComment() + "'" +
            ", changeDate='" + getChangeDate() + "'" +
            ", attachmentFile=" + getAttachmentFile() +
            "}";
    }
}
