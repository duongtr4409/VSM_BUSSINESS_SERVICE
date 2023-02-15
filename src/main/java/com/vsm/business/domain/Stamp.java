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
 * A Stamp.
 */
@Entity
@Table(name = "stamp")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "stamp")
public class Stamp implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "stamp_name")
    private String stampName;

    @Column(name = "stamp_code")
    private String stampCode;

    @Column(name = "created_name")
    private String createdName;

    @Column(name = "owner_name")
    private String ownerName;

    @Column(name = "owner_address")
    private String ownerAddress;

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

    @OneToMany(mappedBy = "stamp")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "requestData", "stamp", "orgReturn", "creater", "modifier", "orgStorages", "attachmentFiles" },
        allowSetters = true
    )
    private Set<ManageStampInfo> manageStampInfos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Stamp id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStampName() {
        return this.stampName;
    }

    public Stamp stampName(String stampName) {
        this.setStampName(stampName);
        return this;
    }

    public void setStampName(String stampName) {
        this.stampName = stampName;
    }

    public String getStampCode() {
        return this.stampCode;
    }

    public Stamp stampCode(String stampCode) {
        this.setStampCode(stampCode);
        return this;
    }

    public void setStampCode(String stampCode) {
        this.stampCode = stampCode;
    }

    public String getCreatedName() {
        return this.createdName;
    }

    public Stamp createdName(String createdName) {
        this.setCreatedName(createdName);
        return this;
    }

    public void setCreatedName(String createdName) {
        this.createdName = createdName;
    }

    public String getOwnerName() {
        return this.ownerName;
    }

    public Stamp ownerName(String ownerName) {
        this.setOwnerName(ownerName);
        return this;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerAddress() {
        return this.ownerAddress;
    }

    public Stamp ownerAddress(String ownerAddress) {
        this.setOwnerAddress(ownerAddress);
        return this;
    }

    public void setOwnerAddress(String ownerAddress) {
        this.ownerAddress = ownerAddress;
    }

    public String getCreatedOrgName() {
        return this.createdOrgName;
    }

    public Stamp createdOrgName(String createdOrgName) {
        this.setCreatedOrgName(createdOrgName);
        return this;
    }

    public void setCreatedOrgName(String createdOrgName) {
        this.createdOrgName = createdOrgName;
    }

    public String getCreatedRankName() {
        return this.createdRankName;
    }

    public Stamp createdRankName(String createdRankName) {
        this.setCreatedRankName(createdRankName);
        return this;
    }

    public void setCreatedRankName(String createdRankName) {
        this.createdRankName = createdRankName;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public Stamp createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedName() {
        return this.modifiedName;
    }

    public Stamp modifiedName(String modifiedName) {
        this.setModifiedName(modifiedName);
        return this;
    }

    public void setModifiedName(String modifiedName) {
        this.modifiedName = modifiedName;
    }

    public Instant getModifiedDate() {
        return this.modifiedDate;
    }

    public Stamp modifiedDate(Instant modifiedDate) {
        this.setModifiedDate(modifiedDate);
        return this;
    }

    public void setModifiedDate(Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public Stamp isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getIsDelete() {
        return this.isDelete;
    }

    public Stamp isDelete(Boolean isDelete) {
        this.setIsDelete(isDelete);
        return this;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public Set<ManageStampInfo> getManageStampInfos() {
        return this.manageStampInfos;
    }

    public void setManageStampInfos(Set<ManageStampInfo> manageStampInfos) {
        if (this.manageStampInfos != null) {
            this.manageStampInfos.forEach(i -> i.setStamp(null));
        }
        if (manageStampInfos != null) {
            manageStampInfos.forEach(i -> i.setStamp(this));
        }
        this.manageStampInfos = manageStampInfos;
    }

    public Stamp manageStampInfos(Set<ManageStampInfo> manageStampInfos) {
        this.setManageStampInfos(manageStampInfos);
        return this;
    }

    public Stamp addManageStampInfo(ManageStampInfo manageStampInfo) {
        this.manageStampInfos.add(manageStampInfo);
        manageStampInfo.setStamp(this);
        return this;
    }

    public Stamp removeManageStampInfo(ManageStampInfo manageStampInfo) {
        this.manageStampInfos.remove(manageStampInfo);
        manageStampInfo.setStamp(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Stamp)) {
            return false;
        }
        return id != null && id.equals(((Stamp) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Stamp{" +
            "id=" + getId() +
            ", stampName='" + getStampName() + "'" +
            ", stampCode='" + getStampCode() + "'" +
            ", createdName='" + getCreatedName() + "'" +
            ", ownerName='" + getOwnerName() + "'" +
            ", ownerAddress='" + getOwnerAddress() + "'" +
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
