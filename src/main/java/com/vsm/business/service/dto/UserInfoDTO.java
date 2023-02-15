package com.vsm.business.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Lob;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.vsm.business.domain.UserInfo} entity.
 */
public class UserInfoDTO implements Serializable {

    private Long id;

    private String name;

    private String fullName;

    private String address;

    private String contactAddress;

    private String identification;

    private Instant issuseDate;

    private String issuseOrg;

    private String fax;

    private String avatar;

    private String email;

    @JsonIgnore
    private String userName;

    @JsonIgnore
    private String password;

    @JsonIgnore
    private String passwordEncode;

    @JsonIgnore
    private Instant expiryDate;

    @JsonIgnore
    private Long numberOfLoginFailed;

    private String description;

    private Boolean isLocked;

    private String userTypeName;

    private String userTypeCode;

    private String userType;

    private String signTypeName;

    private String signTypeCode;

    private String signType;

    private String signFolder;

    private String createdName;

    private String createdOrgName;

    private String createdRankName;

    private Instant createdDate;

    private String modifiedName;

    private Instant modifiedDate;

    @JsonIgnore
    private String idInMicrosoft;

    @JsonIgnore
    private String oDataContext;

    //@JsonIgnore
    private String businessPhones;

    @JsonIgnore
    private String displayName;

    @JsonIgnore
    private String givenName;

    @JsonIgnore
    private String jobTitle;

    @JsonIgnore
    private String mail;

//    @JsonIgnore
    private String mobilePhone;

    @JsonIgnore
    private String officeLocation;

    @JsonIgnore
    private String preferredLanguage;

    @JsonIgnore
    private String surname;

    @JsonIgnore
    private String userPrincipalName;

    @Lob
    @JsonIgnore
    private String infoInMicrosoft;

    private Boolean isActive;

    private Boolean isDelete;

    private String tennantCode;

    private String tennantName;

    private OrganizationDTO leader;

    private TennantDTO tennant;

    private UserInfoDTO created;

    private UserInfoDTO modified;

    private Set<RoleDTO> roles = new HashSet<>();

    private Set<RankDTO> ranks = new HashSet<>();

    private Set<OrganizationDTO> organizations = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactAddress() {
        return contactAddress;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public Instant getIssuseDate() {
        return issuseDate;
    }

    public void setIssuseDate(Instant issuseDate) {
        this.issuseDate = issuseDate;
    }

    public String getIssuseOrg() {
        return issuseOrg;
    }

    public void setIssuseOrg(String issuseOrg) {
        this.issuseOrg = issuseOrg;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordEncode() {
        return passwordEncode;
    }

    public void setPasswordEncode(String passwordEncode) {
        this.passwordEncode = passwordEncode;
    }

    public Instant getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Instant expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Long getNumberOfLoginFailed() {
        return numberOfLoginFailed;
    }

    public void setNumberOfLoginFailed(Long numberOfLoginFailed) {
        this.numberOfLoginFailed = numberOfLoginFailed;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(Boolean isLocked) {
        this.isLocked = isLocked;
    }

    public String getUserTypeName() {
        return userTypeName;
    }

    public void setUserTypeName(String userTypeName) {
        this.userTypeName = userTypeName;
    }

    public String getUserTypeCode() {
        return userTypeCode;
    }

    public void setUserTypeCode(String userTypeCode) {
        this.userTypeCode = userTypeCode;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getSignTypeName() {
        return signTypeName;
    }

    public void setSignTypeName(String signTypeName) {
        this.signTypeName = signTypeName;
    }

    public String getSignTypeCode() {
        return signTypeCode;
    }

    public void setSignTypeCode(String signTypeCode) {
        this.signTypeCode = signTypeCode;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getSignFolder() {
        return signFolder;
    }

    public void setSignFolder(String signFolder) {
        this.signFolder = signFolder;
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

    public String getIdInMicrosoft() {
        return idInMicrosoft;
    }

    public void setIdInMicrosoft(String idInMicrosoft) {
        this.idInMicrosoft = idInMicrosoft;
    }

    public String getoDataContext() {
        return oDataContext;
    }

    public void setoDataContext(String oDataContext) {
        this.oDataContext = oDataContext;
    }

    public String getBusinessPhones() {
        return businessPhones;
    }

    public void setBusinessPhones(String businessPhones) {
        this.businessPhones = businessPhones;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getOfficeLocation() {
        return officeLocation;
    }

    public void setOfficeLocation(String officeLocation) {
        this.officeLocation = officeLocation;
    }

    public String getPreferredLanguage() {
        return preferredLanguage;
    }

    public void setPreferredLanguage(String preferredLanguage) {
        this.preferredLanguage = preferredLanguage;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUserPrincipalName() {
        return userPrincipalName;
    }

    public void setUserPrincipalName(String userPrincipalName) {
        this.userPrincipalName = userPrincipalName;
    }

    public String getInfoInMicrosoft() {
        return infoInMicrosoft;
    }

    public void setInfoInMicrosoft(String infoInMicrosoft) {
        this.infoInMicrosoft = infoInMicrosoft;
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

    public String getTennantCode() {
        return tennantCode;
    }

    public void setTennantCode(String tennantCode) {
        this.tennantCode = tennantCode;
    }

    public String getTennantName() {
        return tennantName;
    }

    public void setTennantName(String tennantName) {
        this.tennantName = tennantName;
    }

    public OrganizationDTO getLeader() {
        return leader;
    }

    public void setLeader(OrganizationDTO leader) {
        this.leader = leader;
    }

    public TennantDTO getTennant() {
        return tennant;
    }

    public void setTennant(TennantDTO tennant) {
        this.tennant = tennant;
    }

    public UserInfoDTO getCreated() {
        return created;
    }

    public void setCreated(UserInfoDTO created) {
        this.created = created;
    }

    public UserInfoDTO getModified() {
        return modified;
    }

    public void setModified(UserInfoDTO modified) {
        this.modified = modified;
    }

    public Set<RoleDTO> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleDTO> roles) {
        this.roles = roles;
    }

    public Set<RankDTO> getRanks() {
        return ranks;
    }

    public void setRanks(Set<RankDTO> ranks) {
        this.ranks = ranks;
    }

    public Set<OrganizationDTO> getOrganizations() {
        return organizations;
    }

    public void setOrganizations(Set<OrganizationDTO> organizations) {
        this.organizations = organizations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserInfoDTO)) {
            return false;
        }

        UserInfoDTO userInfoDTO = (UserInfoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, userInfoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserInfoDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", fullName='" + getFullName() + "'" +
            ", address='" + getAddress() + "'" +
            ", contactAddress='" + getContactAddress() + "'" +
            ", identification='" + getIdentification() + "'" +
            ", issuseDate='" + getIssuseDate() + "'" +
            ", issuseOrg='" + getIssuseOrg() + "'" +
            ", fax='" + getFax() + "'" +
            ", avatar='" + getAvatar() + "'" +
            ", email='" + getEmail() + "'" +
            ", userName='" + getUserName() + "'" +
            ", password='" + getPassword() + "'" +
            ", passwordEncode='" + getPasswordEncode() + "'" +
            ", expiryDate='" + getExpiryDate() + "'" +
            ", numberOfLoginFailed=" + getNumberOfLoginFailed() +
            ", description='" + getDescription() + "'" +
            ", isLocked='" + getIsLocked() + "'" +
            ", userTypeName='" + getUserTypeName() + "'" +
            ", userTypeCode='" + getUserTypeCode() + "'" +
            ", userType='" + getUserType() + "'" +
            ", signTypeName='" + getSignTypeName() + "'" +
            ", signTypeCode='" + getSignTypeCode() + "'" +
            ", signType='" + getSignType() + "'" +
            ", signFolder='" + getSignFolder() + "'" +
            ", createdName='" + getCreatedName() + "'" +
            ", createdOrgName='" + getCreatedOrgName() + "'" +
            ", createdRankName='" + getCreatedRankName() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", modifiedName='" + getModifiedName() + "'" +
            ", modifiedDate='" + getModifiedDate() + "'" +
            ", idInMicrosoft='" + getIdInMicrosoft() + "'" +
            ", oDataContext='" + getoDataContext() + "'" +
            ", businessPhones='" + getBusinessPhones() + "'" +
            ", displayName='" + getDisplayName() + "'" +
            ", givenName='" + getGivenName() + "'" +
            ", jobTitle='" + getJobTitle() + "'" +
            ", mail='" + getMail() + "'" +
            ", mobilePhone='" + getMobilePhone() + "'" +
            ", officeLocation='" + getOfficeLocation() + "'" +
            ", preferredLanguage='" + getPreferredLanguage() + "'" +
            ", surname='" + getSurname() + "'" +
            ", userPrincipalName='" + getUserPrincipalName() + "'" +
            ", infoInMicrosoft='" + getInfoInMicrosoft() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", isDelete='" + getIsDelete() + "'" +
            ", tennantCode='" + getTennantCode() + "'" +
            ", tennantName='" + getTennantName() + "'" +
            ", leader=" + getLeader() +
            ", tennant=" + getTennant() +
            ", created=" + getCreated() +
            ", modified=" + getModified() +
            ", roles=" + getRoles() +
            ", ranks=" + getRanks() +
            ", organizations=" + getOrganizations() +
            "}";
    }
}
