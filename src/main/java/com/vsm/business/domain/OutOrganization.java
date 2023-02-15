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
 * A OutOrganization.
 */
@Entity
@Table(name = "out_organization")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "outorganization")
public class OutOrganization implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "out_org_name")
    private String outOrgName;

    @Column(name = "out_org_code")
    private String outOrgCode;

    @Column(name = "short_description")
    private String shortDescription;

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

    @OneToMany(mappedBy = "outOrganization")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "dispatchBook",
            "releaseOrg",
            "composeOrg",
            "ownerOrg",
            "signer",
            "officialDispatchType",
            "documentType",
            "priorityLevel",
            "securityLevel",
            "officialDispatchStatus",
            "creater",
            "modifier",
            "outOrganization",
            "offDispatchUserReads",
            "attachmentFiles",
            "officialDispatchHis",
            "stepProcessDocs",
        },
        allowSetters = true
    )
    private Set<OfficialDispatch> officialDispatches = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public OutOrganization id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOutOrgName() {
        return this.outOrgName;
    }

    public OutOrganization outOrgName(String outOrgName) {
        this.setOutOrgName(outOrgName);
        return this;
    }

    public void setOutOrgName(String outOrgName) {
        this.outOrgName = outOrgName;
    }

    public String getOutOrgCode() {
        return this.outOrgCode;
    }

    public OutOrganization outOrgCode(String outOrgCode) {
        this.setOutOrgCode(outOrgCode);
        return this;
    }

    public void setOutOrgCode(String outOrgCode) {
        this.outOrgCode = outOrgCode;
    }

    public String getShortDescription() {
        return this.shortDescription;
    }

    public OutOrganization shortDescription(String shortDescription) {
        this.setShortDescription(shortDescription);
        return this;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return this.description;
    }

    public OutOrganization description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedName() {
        return this.createdName;
    }

    public OutOrganization createdName(String createdName) {
        this.setCreatedName(createdName);
        return this;
    }

    public void setCreatedName(String createdName) {
        this.createdName = createdName;
    }

    public String getCreatedOrgName() {
        return this.createdOrgName;
    }

    public OutOrganization createdOrgName(String createdOrgName) {
        this.setCreatedOrgName(createdOrgName);
        return this;
    }

    public void setCreatedOrgName(String createdOrgName) {
        this.createdOrgName = createdOrgName;
    }

    public String getCreatedRankName() {
        return this.createdRankName;
    }

    public OutOrganization createdRankName(String createdRankName) {
        this.setCreatedRankName(createdRankName);
        return this;
    }

    public void setCreatedRankName(String createdRankName) {
        this.createdRankName = createdRankName;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public OutOrganization createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedName() {
        return this.modifiedName;
    }

    public OutOrganization modifiedName(String modifiedName) {
        this.setModifiedName(modifiedName);
        return this;
    }

    public void setModifiedName(String modifiedName) {
        this.modifiedName = modifiedName;
    }

    public Instant getModifiedDate() {
        return this.modifiedDate;
    }

    public OutOrganization modifiedDate(Instant modifiedDate) {
        this.setModifiedDate(modifiedDate);
        return this;
    }

    public void setModifiedDate(Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public OutOrganization isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getIsDelete() {
        return this.isDelete;
    }

    public OutOrganization isDelete(Boolean isDelete) {
        this.setIsDelete(isDelete);
        return this;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public Set<OfficialDispatch> getOfficialDispatches() {
        return this.officialDispatches;
    }

    public void setOfficialDispatches(Set<OfficialDispatch> officialDispatches) {
        if (this.officialDispatches != null) {
            this.officialDispatches.forEach(i -> i.setOutOrganization(null));
        }
        if (officialDispatches != null) {
            officialDispatches.forEach(i -> i.setOutOrganization(this));
        }
        this.officialDispatches = officialDispatches;
    }

    public OutOrganization officialDispatches(Set<OfficialDispatch> officialDispatches) {
        this.setOfficialDispatches(officialDispatches);
        return this;
    }

    public OutOrganization addOfficialDispatch(OfficialDispatch officialDispatch) {
        this.officialDispatches.add(officialDispatch);
        officialDispatch.setOutOrganization(this);
        return this;
    }

    public OutOrganization removeOfficialDispatch(OfficialDispatch officialDispatch) {
        this.officialDispatches.remove(officialDispatch);
        officialDispatch.setOutOrganization(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OutOrganization)) {
            return false;
        }
        return id != null && id.equals(((OutOrganization) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OutOrganization{" +
            "id=" + getId() +
            ", outOrgName='" + getOutOrgName() + "'" +
            ", outOrgCode='" + getOutOrgCode() + "'" +
            ", shortDescription='" + getShortDescription() + "'" +
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
