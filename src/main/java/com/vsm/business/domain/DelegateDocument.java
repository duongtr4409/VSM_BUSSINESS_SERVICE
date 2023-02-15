package com.vsm.business.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A DelegateDocument.
 */
@Entity
@Table(name = "delegate_document")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "delegatedocument")
public class DelegateDocument implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "delegate_doc_name")
    private String delegateDocName;

    @Column(name = "delegate_doc_code")
    private String delegateDocCode;

    @Column(name = "description")
    private String description;

    @Column(name = "created_name")
    private String createdName;

    @Column(name = "created_org_name")
    private String createdOrgName;

    @Column(name = "created_rank_name")
    private String createdRankName;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "modified_name")
    private String modifiedName;

    @Column(name = "modified_date")
    private Instant modifiedDate;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "is_delete")
    private Boolean isDelete;

    @OneToMany(mappedBy = "delegateDocument")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "creater", "modifier", "delegater", "delegated", "delegateType", "docDelegate", "delegateDocument" },
        allowSetters = true
    )
    private Set<DelegateInfo> delegateInfos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DelegateDocument id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDelegateDocName() {
        return this.delegateDocName;
    }

    public DelegateDocument delegateDocName(String delegateDocName) {
        this.setDelegateDocName(delegateDocName);
        return this;
    }

    public void setDelegateDocName(String delegateDocName) {
        this.delegateDocName = delegateDocName;
    }

    public String getDelegateDocCode() {
        return this.delegateDocCode;
    }

    public DelegateDocument delegateDocCode(String delegateDocCode) {
        this.setDelegateDocCode(delegateDocCode);
        return this;
    }

    public void setDelegateDocCode(String delegateDocCode) {
        this.delegateDocCode = delegateDocCode;
    }

    public String getDescription() {
        return this.description;
    }

    public DelegateDocument description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedName() {
        return this.createdName;
    }

    public DelegateDocument createdName(String createdName) {
        this.setCreatedName(createdName);
        return this;
    }

    public void setCreatedName(String createdName) {
        this.createdName = createdName;
    }

    public String getCreatedOrgName() {
        return this.createdOrgName;
    }

    public DelegateDocument createdOrgName(String createdOrgName) {
        this.setCreatedOrgName(createdOrgName);
        return this;
    }

    public void setCreatedOrgName(String createdOrgName) {
        this.createdOrgName = createdOrgName;
    }

    public String getCreatedRankName() {
        return this.createdRankName;
    }

    public DelegateDocument createdRankName(String createdRankName) {
        this.setCreatedRankName(createdRankName);
        return this;
    }

    public void setCreatedRankName(String createdRankName) {
        this.createdRankName = createdRankName;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public DelegateDocument createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedName() {
        return this.modifiedName;
    }

    public DelegateDocument modifiedName(String modifiedName) {
        this.setModifiedName(modifiedName);
        return this;
    }

    public void setModifiedName(String modifiedName) {
        this.modifiedName = modifiedName;
    }

    public Instant getModifiedDate() {
        return this.modifiedDate;
    }

    public DelegateDocument modifiedDate(Instant modifiedDate) {
        this.setModifiedDate(modifiedDate);
        return this;
    }

    public void setModifiedDate(Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public DelegateDocument isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getIsDelete() {
        return this.isDelete;
    }

    public DelegateDocument isDelete(Boolean isDelete) {
        this.setIsDelete(isDelete);
        return this;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public Set<DelegateInfo> getDelegateInfos() {
        return this.delegateInfos;
    }

    public void setDelegateInfos(Set<DelegateInfo> delegateInfos) {
        if (this.delegateInfos != null) {
            this.delegateInfos.forEach(i -> i.setDelegateDocument(null));
        }
        if (delegateInfos != null) {
            delegateInfos.forEach(i -> i.setDelegateDocument(this));
        }
        this.delegateInfos = delegateInfos;
    }

    public DelegateDocument delegateInfos(Set<DelegateInfo> delegateInfos) {
        this.setDelegateInfos(delegateInfos);
        return this;
    }

    public DelegateDocument addDelegateInfo(DelegateInfo delegateInfo) {
        this.delegateInfos.add(delegateInfo);
        delegateInfo.setDelegateDocument(this);
        return this;
    }

    public DelegateDocument removeDelegateInfo(DelegateInfo delegateInfo) {
        this.delegateInfos.remove(delegateInfo);
        delegateInfo.setDelegateDocument(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DelegateDocument)) {
            return false;
        }
        return id != null && id.equals(((DelegateDocument) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DelegateDocument{" +
            "id=" + getId() +
            ", delegateDocName='" + getDelegateDocName() + "'" +
            ", delegateDocCode='" + getDelegateDocCode() + "'" +
            ", description='" + getDescription() + "'" +
            ", createdName='" + getCreatedName() + "'" +
            ", createdOrgName='" + getCreatedOrgName() + "'" +
            ", createdRankName='" + getCreatedRankName() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", modifiedName='" + getModifiedName() + "'" +
            ", modifiedDate='" + getModifiedDate() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", isDelete='" + getIsDelete() + "'" +
            "}";
    }
}
