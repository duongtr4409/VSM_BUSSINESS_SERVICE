package com.vsm.business.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A Feature.
 */
@Entity
@Table(name = "feature")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "feature")
public class Feature implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "feature_name")
    private String featureName;

    @Column(name = "feature_code")
    private String featureCode;

    @Column(name = "link")
    private String link;

    @Column(name = "is_default")
    private String isDefault;

    @Column(name = "description")
    private String description;

    @Column(name = "feature_type")
    private String featureType;

    @Column(name = "feature_type_code")
    private String featureTypeCode;

    @Column(name = "feature_type_name")
    private String featureTypeName;

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

    @Column(name = "tennant_code")
    private String tennantCode;

    @Column(name = "tennant_name")
    private String tennantName;

    @ManyToMany(mappedBy = "features")
    @JsonIgnoreProperties(
        value = { "tennant", "created", "modified", "features", "roleRequests", "roleOrganizations", "userInfos", "userGroups" },
        allowSetters = true
    )
    private Set<Role> roles = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Feature id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFeatureName() {
        return this.featureName;
    }

    public Feature featureName(String featureName) {
        this.setFeatureName(featureName);
        return this;
    }

    public void setFeatureName(String featureName) {
        this.featureName = featureName;
    }

    public String getFeatureCode() {
        return this.featureCode;
    }

    public Feature featureCode(String featureCode) {
        this.setFeatureCode(featureCode);
        return this;
    }

    public void setFeatureCode(String featureCode) {
        this.featureCode = featureCode;
    }

    public String getLink() {
        return this.link;
    }

    public Feature link(String link) {
        this.setLink(link);
        return this;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getIsDefault() {
        return this.isDefault;
    }

    public Feature isDefault(String isDefault) {
        this.setIsDefault(isDefault);
        return this;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public String getDescription() {
        return this.description;
    }

    public Feature description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setFeatureType(String featureType) {
        this.featureType = featureType;
    }

    public String getFeatureType(){
        return this.featureType;
    }

    public Feature featureType(String featureType){
        this.setFeatureType(featureType);
        return this;
    }

    public void setFeatureTypeCode(String featureTypeCode){
        this.featureTypeCode = featureTypeCode;
    }

    public String getFeatureTypeCode(String featureTypeCode){
        return this.featureTypeCode;
    }

    public Feature featureTypeCode(String featureTypeCode){
        this.setFeatureTypeCode(featureTypeCode);
        return this;
    }

    public void setFeatureTypeName(String featureTypeName){
        this.featureTypeName = featureTypeName;
    }

    public String getFeatureTypeName(){
        return this.featureTypeName;
    }

    public Feature featureTypeName(String featureTypeName){
        this.setFeatureTypeName(featureTypeName);
        return this;
    }

    public String getCreatedName() {
        return this.createdName;
    }

    public Feature createdName(String createdName) {
        this.setCreatedName(createdName);
        return this;
    }

    public void setCreatedName(String createdName) {
        this.createdName = createdName;
    }

    public String getCreatedOrgName() {
        return this.createdOrgName;
    }

    public Feature createdOrgName(String createdOrgName) {
        this.setCreatedOrgName(createdOrgName);
        return this;
    }

    public void setCreatedOrgName(String createdOrgName) {
        this.createdOrgName = createdOrgName;
    }

    public String getCreatedRankName() {
        return this.createdRankName;
    }

    public Feature createdRankName(String createdRankName) {
        this.setCreatedRankName(createdRankName);
        return this;
    }

    public void setCreatedRankName(String createdRankName) {
        this.createdRankName = createdRankName;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public Feature createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedName() {
        return this.modifiedName;
    }

    public Feature modifiedName(String modifiedName) {
        this.setModifiedName(modifiedName);
        return this;
    }

    public void setModifiedName(String modifiedName) {
        this.modifiedName = modifiedName;
    }

    public Instant getModifiedDate() {
        return this.modifiedDate;
    }

    public Feature modifiedDate(Instant modifiedDate) {
        this.setModifiedDate(modifiedDate);
        return this;
    }

    public void setModifiedDate(Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public Feature isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getIsDelete() {
        return this.isDelete;
    }

    public Feature isDelete(Boolean isDelete) {
        this.setIsDelete(isDelete);
        return this;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public String getTennantCode() {
        return this.tennantCode;
    }

    public Feature tennantCode(String tennantCode) {
        this.setTennantCode(tennantCode);
        return this;
    }

    public void setTennantCode(String tennantCode) {
        this.tennantCode = tennantCode;
    }

    public String getTennantName() {
        return this.tennantName;
    }

    public Feature tennantName(String tennantName) {
        this.setTennantName(tennantName);
        return this;
    }

    public void setTennantName(String tennantName) {
        this.tennantName = tennantName;
    }

    public Set<Role> getRoles() {
        return this.roles;
    }

    public void setRoles(Set<Role> roles) {
        if (this.roles != null) {
            this.roles.forEach(i -> i.removeFeature(this));
        }
        if (roles != null) {
            roles.forEach(i -> i.addFeature(this));
        }
        this.roles = roles;
    }

    public Feature roles(Set<Role> roles) {
        this.setRoles(roles);
        return this;
    }

    public Feature addRole(Role role) {
        this.roles.add(role);
        role.getFeatures().add(this);
        return this;
    }

    public Feature removeRole(Role role) {
        this.roles.remove(role);
        role.getFeatures().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Feature)) {
            return false;
        }
        return id != null && id.equals(((Feature) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Feature{" +
            "id=" + getId() +
            ", featureName='" + getFeatureName() + "'" +
            ", featureCode='" + getFeatureCode() + "'" +
            ", link='" + getLink() + "'" +
            ", isDefault='" + getIsDefault() + "'" +
            ", description='" + getDescription() + "'" +
            ", createdName='" + getCreatedName() + "'" +
            ", createdOrgName='" + getCreatedOrgName() + "'" +
            ", createdRankName='" + getCreatedRankName() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", modifiedName='" + getModifiedName() + "'" +
            ", modifiedDate='" + getModifiedDate() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", isDelete='" + getIsDelete() + "'" +
            ", tennantCode='" + getTennantCode() + "'" +
            ", tennantName='" + getTennantName() + "'" +
            "}";
    }
}
