package com.vsm.business.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A RankInOrg.
 */
@Entity
@Table(name = "rank_in_org")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "rankinorg")
public class RankInOrg implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "rank_name")
    private String rankName;

    @Column(name = "rank_code")
    private String rankCode;

    @Column(name = "org_name")
    private String orgName;

    @Column(name = "org_code")
    private String orgCode;

    @Column(name = "description")
    private String description;

    @Column(name = "created_name")
    private String createdName;

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

    @ManyToOne
    @JsonIgnoreProperties(value = { "tennant", "created", "modified", "rankInOrgs", "userInfos" }, allowSetters = true)
    private Rank rank;

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "orgParent",
            "leader",
            "requestData",
            "dispatchBooks",
            "transferHandles",
            "rankInOrgs",
            "roleOrganizations",
            "templateForms",
            "userInfos",
            "processInfos",
            "dispatchBookOrgs",
            "mailTemplates",
            "orgStorages",
        },
        allowSetters = true
    )
    private Organization organization;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public RankInOrg id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRankName() {
        return this.rankName;
    }

    public RankInOrg rankName(String rankName) {
        this.setRankName(rankName);
        return this;
    }

    public void setRankName(String rankName) {
        this.rankName = rankName;
    }

    public String getRankCode() {
        return this.rankCode;
    }

    public RankInOrg rankCode(String rankCode) {
        this.setRankCode(rankCode);
        return this;
    }

    public void setRankCode(String rankCode) {
        this.rankCode = rankCode;
    }

    public String getOrgName() {
        return this.orgName;
    }

    public RankInOrg orgName(String orgName) {
        this.setOrgName(orgName);
        return this;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgCode() {
        return this.orgCode;
    }

    public RankInOrg orgCode(String orgCode) {
        this.setOrgCode(orgCode);
        return this;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getDescription() {
        return this.description;
    }

    public RankInOrg description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedName() {
        return this.createdName;
    }

    public RankInOrg createdName(String createdName) {
        this.setCreatedName(createdName);
        return this;
    }

    public void setCreatedName(String createdName) {
        this.createdName = createdName;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public RankInOrg createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedName() {
        return this.modifiedName;
    }

    public RankInOrg modifiedName(String modifiedName) {
        this.setModifiedName(modifiedName);
        return this;
    }

    public void setModifiedName(String modifiedName) {
        this.modifiedName = modifiedName;
    }

    public Instant getModifiedDate() {
        return this.modifiedDate;
    }

    public RankInOrg modifiedDate(Instant modifiedDate) {
        this.setModifiedDate(modifiedDate);
        return this;
    }

    public void setModifiedDate(Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public RankInOrg isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getIsDelete() {
        return this.isDelete;
    }

    public RankInOrg isDelete(Boolean isDelete) {
        this.setIsDelete(isDelete);
        return this;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public Rank getRank() {
        return this.rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public RankInOrg rank(Rank rank) {
        this.setRank(rank);
        return this;
    }

    public Organization getOrganization() {
        return this.organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public RankInOrg organization(Organization organization) {
        this.setOrganization(organization);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RankInOrg)) {
            return false;
        }
        return id != null && id.equals(((RankInOrg) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RankInOrg{" +
            "id=" + getId() +
            ", rankName='" + getRankName() + "'" +
            ", rankCode='" + getRankCode() + "'" +
            ", orgName='" + getOrgName() + "'" +
            ", orgCode='" + getOrgCode() + "'" +
            ", description='" + getDescription() + "'" +
            ", createdName='" + getCreatedName() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", modifiedName='" + getModifiedName() + "'" +
            ", modifiedDate='" + getModifiedDate() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", isDelete='" + getIsDelete() + "'" +
            "}";
    }
}
