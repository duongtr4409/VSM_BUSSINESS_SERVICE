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
 * A DelegateType.
 */
@Entity
@Table(name = "delegate_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "delegatetype")
public class DelegateType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "delegate_type_code")
    private String delegateTypeCode;

    @Column(name = "delegate_type_name")
    private String delegateTypeName;

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

    @OneToMany(mappedBy = "delegateType")
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

    public DelegateType id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDelegateTypeCode() {
        return this.delegateTypeCode;
    }

    public DelegateType delegateTypeCode(String delegateTypeCode) {
        this.setDelegateTypeCode(delegateTypeCode);
        return this;
    }

    public void setDelegateTypeCode(String delegateTypeCode) {
        this.delegateTypeCode = delegateTypeCode;
    }

    public String getDelegateTypeName() {
        return this.delegateTypeName;
    }

    public DelegateType delegateTypeName(String delegateTypeName) {
        this.setDelegateTypeName(delegateTypeName);
        return this;
    }

    public void setDelegateTypeName(String delegateTypeName) {
        this.delegateTypeName = delegateTypeName;
    }

    public String getDescription() {
        return this.description;
    }

    public DelegateType description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedName() {
        return this.createdName;
    }

    public DelegateType createdName(String createdName) {
        this.setCreatedName(createdName);
        return this;
    }

    public void setCreatedName(String createdName) {
        this.createdName = createdName;
    }

    public String getCreatedOrgName() {
        return this.createdOrgName;
    }

    public DelegateType createdOrgName(String createdOrgName) {
        this.setCreatedOrgName(createdOrgName);
        return this;
    }

    public void setCreatedOrgName(String createdOrgName) {
        this.createdOrgName = createdOrgName;
    }

    public String getCreatedRankName() {
        return this.createdRankName;
    }

    public DelegateType createdRankName(String createdRankName) {
        this.setCreatedRankName(createdRankName);
        return this;
    }

    public void setCreatedRankName(String createdRankName) {
        this.createdRankName = createdRankName;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public DelegateType createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedName() {
        return this.modifiedName;
    }

    public DelegateType modifiedName(String modifiedName) {
        this.setModifiedName(modifiedName);
        return this;
    }

    public void setModifiedName(String modifiedName) {
        this.modifiedName = modifiedName;
    }

    public Instant getModifiedDate() {
        return this.modifiedDate;
    }

    public DelegateType modifiedDate(Instant modifiedDate) {
        this.setModifiedDate(modifiedDate);
        return this;
    }

    public void setModifiedDate(Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public DelegateType isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getIsDelete() {
        return this.isDelete;
    }

    public DelegateType isDelete(Boolean isDelete) {
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
            this.delegateInfos.forEach(i -> i.setDelegateType(null));
        }
        if (delegateInfos != null) {
            delegateInfos.forEach(i -> i.setDelegateType(this));
        }
        this.delegateInfos = delegateInfos;
    }

    public DelegateType delegateInfos(Set<DelegateInfo> delegateInfos) {
        this.setDelegateInfos(delegateInfos);
        return this;
    }

    public DelegateType addDelegateInfo(DelegateInfo delegateInfo) {
        this.delegateInfos.add(delegateInfo);
        delegateInfo.setDelegateType(this);
        return this;
    }

    public DelegateType removeDelegateInfo(DelegateInfo delegateInfo) {
        this.delegateInfos.remove(delegateInfo);
        delegateInfo.setDelegateType(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DelegateType)) {
            return false;
        }
        return id != null && id.equals(((DelegateType) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DelegateType{" +
            "id=" + getId() +
            ", delegateTypeCode='" + getDelegateTypeCode() + "'" +
            ", delegateTypeName='" + getDelegateTypeName() + "'" +
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
