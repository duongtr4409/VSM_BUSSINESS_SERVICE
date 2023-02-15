package com.vsm.business.service.authenicate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vsm.business.service.dto.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

public class BasicUserInfoDTO implements Serializable {

    public static BasicUserInfoDTO fromUserInfoDTO(UserInfoDTO userInfoDTO) {
        BasicUserInfoDTO result = new BasicUserInfoDTO();
        result.setId(userInfoDTO.getId());
        result.setName(userInfoDTO.getName());
        result.setFullName(userInfoDTO.getFullName());
        result.setAddress(userInfoDTO.getAddress());
        result.setContactAddress(userInfoDTO.getContactAddress());
        result.setIdentification(userInfoDTO.getIdentification());
        result.setIssuseDate(userInfoDTO.getIssuseDate());
        result.setIssuseOrg(userInfoDTO.getIssuseOrg());
        result.setFax(userInfoDTO.getFax());
        result.setAvatar(userInfoDTO.getAvatar());
        result.setEmail(userInfoDTO.getEmail());
        result.setUserName(userInfoDTO.getUserName());
        result.setDescription(userInfoDTO.getDescription());
        result.setIdInMicrosoft(userInfoDTO.getIdInMicrosoft());
        result.setBusinessPhones(userInfoDTO.getBusinessPhones());
        result.setDisplayName(userInfoDTO.getDisplayName());
        result.setGivenName(userInfoDTO.getGivenName());
        result.setJobTitle(userInfoDTO.getJobTitle());
        result.setMail(userInfoDTO.getMail());
        result.setOfficeLocation(userInfoDTO.getOfficeLocation());
        result.setMobilePhone(userInfoDTO.getMobilePhone());
        result.setPreferredLanguage(userInfoDTO.getPreferredLanguage());
        result.setSurname((userInfoDTO.getSurname()));
        result.setUserPrincipalName(userInfoDTO.getUserPrincipalName());
        result.setInfoInMicrosoft(userInfoDTO.getInfoInMicrosoft());
        result.setRoles(userInfoDTO.getRoles());
        result.setRanks(userInfoDTO.getRanks());
        result.setOrganizations(userInfoDTO.getOrganizations());
        result.setLeader(userInfoDTO.getLeader());
        return result;
    }

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

    private String userName;

    private String description;

    private String idInMicrosoft;

    private String oDataContext;

    private String businessPhones;

    private String displayName;

    private String givenName;

    private String jobTitle;

    private String mail;

    private String mobilePhone;

    private String officeLocation;

    private String preferredLanguage;

    private String surname;

    private String userPrincipalName;

    @JsonIgnore
    private String infoInMicrosoft;

    private Set<RoleDTO> roles = new HashSet<>();

    private Set<RankDTO> ranks = new HashSet<>();

    private Set<OrganizationDTO> organizations = new HashSet<>();

    private OrganizationDTO leader;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public OrganizationDTO getLeader() {
        return leader;
    }

    public void setLeader(OrganizationDTO leader) {
        this.leader = leader;
    }
}
