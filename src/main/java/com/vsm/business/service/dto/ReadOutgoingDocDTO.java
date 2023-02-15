package com.vsm.business.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.vsm.business.domain.ReadOutgoingDoc} entity.
 */
public class ReadOutgoingDocDTO implements Serializable {

    private Long id;

    private String outgoingDocName;

    private String outgoingDocCode;

    private String outgoingDocNumber;

    private String readerName;

    private String readerAvatar;

    private String readerEmail;

    private String content;

    private String createdName;

    private String createdOrgName;

    private String createdRankName;

    private Instant createdDate;

    private String modifiedName;

    private Instant modifiedDate;

    private Boolean isActive;

    private Boolean isDelete;

    private OutgoingDocDTO outgoingDoc;

    private UserInfoDTO creater;

    private UserInfoDTO modifier;

    private UserInfoDTO reader;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOutgoingDocName() {
        return outgoingDocName;
    }

    public void setOutgoingDocName(String outgoingDocName) {
        this.outgoingDocName = outgoingDocName;
    }

    public String getOutgoingDocCode() {
        return outgoingDocCode;
    }

    public void setOutgoingDocCode(String outgoingDocCode) {
        this.outgoingDocCode = outgoingDocCode;
    }

    public String getOutgoingDocNumber() {
        return outgoingDocNumber;
    }

    public void setOutgoingDocNumber(String outgoingDocNumber) {
        this.outgoingDocNumber = outgoingDocNumber;
    }

    public String getReaderName() {
        return readerName;
    }

    public void setReaderName(String readerName) {
        this.readerName = readerName;
    }

    public String getReaderAvatar() {
        return readerAvatar;
    }

    public void setReaderAvatar(String readerAvatar) {
        this.readerAvatar = readerAvatar;
    }

    public String getReaderEmail() {
        return readerEmail;
    }

    public void setReaderEmail(String readerEmail) {
        this.readerEmail = readerEmail;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreatedName() {
        return createdName;
    }

    public void setCreatedName(String createdName) {
        this.createdName = createdName;
    }

    public String getCreatedOrgName() {
        return createdOrgName;
    }

    public void setCreatedOrgName(String createdOrgName) {
        this.createdOrgName = createdOrgName;
    }

    public String getCreatedRankName() {
        return createdRankName;
    }

    public void setCreatedRankName(String createdRankName) {
        this.createdRankName = createdRankName;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedName() {
        return modifiedName;
    }

    public void setModifiedName(String modifiedName) {
        this.modifiedName = modifiedName;
    }

    public Instant getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public OutgoingDocDTO getOutgoingDoc() {
        return outgoingDoc;
    }

    public void setOutgoingDoc(OutgoingDocDTO outgoingDoc) {
        this.outgoingDoc = outgoingDoc;
    }

    public UserInfoDTO getCreater() {
        return creater;
    }

    public void setCreater(UserInfoDTO creater) {
        this.creater = creater;
    }

    public UserInfoDTO getModifier() {
        return modifier;
    }

    public void setModifier(UserInfoDTO modifier) {
        this.modifier = modifier;
    }

    public UserInfoDTO getReader() {
        return reader;
    }

    public void setReader(UserInfoDTO reader) {
        this.reader = reader;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReadOutgoingDocDTO)) {
            return false;
        }

        ReadOutgoingDocDTO readOutgoingDocDTO = (ReadOutgoingDocDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, readOutgoingDocDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReadOutgoingDocDTO{" +
            "id=" + getId() +
            ", outgoingDocName='" + getOutgoingDocName() + "'" +
            ", outgoingDocCode='" + getOutgoingDocCode() + "'" +
            ", outgoingDocNumber='" + getOutgoingDocNumber() + "'" +
            ", readerName='" + getReaderName() + "'" +
            ", readerAvatar='" + getReaderAvatar() + "'" +
            ", readerEmail='" + getReaderEmail() + "'" +
            ", content='" + getContent() + "'" +
            ", createdName='" + getCreatedName() + "'" +
            ", createdOrgName='" + getCreatedOrgName() + "'" +
            ", createdRankName='" + getCreatedRankName() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", modifiedName='" + getModifiedName() + "'" +
            ", modifiedDate='" + getModifiedDate() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", isDelete='" + getIsDelete() + "'" +
            ", outgoingDoc=" + getOutgoingDoc() +
            ", creater=" + getCreater() +
            ", modifier=" + getModifier() +
            ", reader=" + getReader() +
            "}";
    }
}
