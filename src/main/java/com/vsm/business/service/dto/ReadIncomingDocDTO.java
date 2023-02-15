package com.vsm.business.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.vsm.business.domain.ReadIncomingDoc} entity.
 */
public class ReadIncomingDocDTO implements Serializable {

    private Long id;

    private String incomingDocName;

    private String incomingDocCode;

    private String incomingDocNumber;

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

    private IncomingDocDTO incomingDoc;

    private UserInfoDTO creater;

    private UserInfoDTO modifier;

    private UserInfoDTO reader;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIncomingDocName() {
        return incomingDocName;
    }

    public void setIncomingDocName(String incomingDocName) {
        this.incomingDocName = incomingDocName;
    }

    public String getIncomingDocCode() {
        return incomingDocCode;
    }

    public void setIncomingDocCode(String incomingDocCode) {
        this.incomingDocCode = incomingDocCode;
    }

    public String getIncomingDocNumber() {
        return incomingDocNumber;
    }

    public void setIncomingDocNumber(String incomingDocNumber) {
        this.incomingDocNumber = incomingDocNumber;
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

    public IncomingDocDTO getIncomingDoc() {
        return incomingDoc;
    }

    public void setIncomingDoc(IncomingDocDTO incomingDoc) {
        this.incomingDoc = incomingDoc;
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
        if (!(o instanceof ReadIncomingDocDTO)) {
            return false;
        }

        ReadIncomingDocDTO readIncomingDocDTO = (ReadIncomingDocDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, readIncomingDocDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReadIncomingDocDTO{" +
            "id=" + getId() +
            ", incomingDocName='" + getIncomingDocName() + "'" +
            ", incomingDocCode='" + getIncomingDocCode() + "'" +
            ", incomingDocNumber='" + getIncomingDocNumber() + "'" +
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
            ", incomingDoc=" + getIncomingDoc() +
            ", creater=" + getCreater() +
            ", modifier=" + getModifier() +
            ", reader=" + getReader() +
            "}";
    }
}
