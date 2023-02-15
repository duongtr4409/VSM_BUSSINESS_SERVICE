package com.vsm.business.service.auth.bo;

import com.vsm.business.domain.*;
import org.elasticsearch.common.Strings;
import org.json.JSONArray;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

public class MyUserDetail implements UserDetails {

    private String userName;

//    private String password;

    //private UserInfo userInfo;


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

//    private String passwordEncode;

    private Instant expiryDate;

    private Long numberOfLoginFailed;

    private String description;

    private Boolean isLocked;

    private String userTypeName;

    private String userTypeCode;

//    private String userType;

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

    private String infoInMicrosoft;

    private Boolean isActive;

    private Boolean isDelete;

    private String tennantCode;

    private String tennantName;

    private Organization leader;

    private Tennant tennant;

    private UserInfo created;

    private UserInfo modified;

    private Set<Role> roles = new HashSet<>();

    private Set<String> rolesString = new HashSet<>();

    private Set<String> apiFeature = new HashSet<>();               // lưu thông tin link các API được phép gọi theo cấu hình tính năng (feature)

    private Set<Rank> ranks = new HashSet<>();

    private Set<Organization> organizations = new HashSet<>();

    private Instant signInDate;

    private Collection<GrantedAuthority> authorities;

//    public MyUserDetail(String userName, String password, UserInfo userInfo) {
//        this.userName = userName;
//        this.password = password;
//        this.userInfo = userInfo;
//
//        this.setAuthorities(this.userInfo);
//
//    }

    public MyUserDetail(String userName, UserInfo userInfo, Collection<GrantedAuthority> authorities) {
        this.userName = userName;
//        this.password = password;
        //this.userInfo = userInfo;
        signInDate = Instant.now();
        if(userInfo != null){
            this.id = userInfo.getId();
            this.name = userInfo.getName();
            this.fullName = userInfo.getFullName();
            this.address = userInfo.getAddress();
            this.fullName = userInfo.getFullName();
            this.contactAddress = userInfo.getContactAddress();
            this.identification = userInfo.getIdentification();
            this.issuseDate = userInfo.getIssuseDate();
            this.fax = userInfo.getIssuseOrg();
            this.avatar = userInfo.getAvatar();
            this.email = userInfo.getEmail();
            this.expiryDate = userInfo.getExpiryDate();
            this.numberOfLoginFailed = userInfo.getNumberOfLoginFailed();
            this.description = userInfo.getDescription();
            this.isLocked = userInfo.getIsLocked();
            this.userTypeName = userInfo.getUserTypeName();
            this.userTypeCode = userInfo.getUserTypeCode();
//            this.userType = userInfo.getUserType();
            this.signTypeName = userInfo.getSignTypeName();
            this.signTypeCode = userInfo.getSignTypeCode();
//            this.userType = userInfo.getUserType();
            this.signFolder = userInfo.getSignFolder();
            this.createdName = userInfo.getCreatedName();
            this.createdOrgName = userInfo.getCreatedOrgName();
            this.createdRankName = userInfo.getCreatedRankName();
            this.createdDate = userInfo.getCreatedDate();
//            this.created = userInfo.getCreated();
            this.modifiedName = userInfo.getModifiedName();
            this.modifiedDate = userInfo.getModifiedDate();
//            this.modified = userInfo.getModified();
            this.idInMicrosoft = userInfo.getIdInMicrosoft();
            this.oDataContext = userInfo.getoDataContext();
            this.businessPhones = userInfo.getBusinessPhones();
            this.displayName = userInfo.getDisplayName();
            this.givenName = userInfo.getGivenName();
            this.jobTitle = userInfo.getJobTitle();
            this.mail = userInfo.getMail();
            this.mobilePhone = userInfo.getMobilePhone();
            this.officeLocation = userInfo.getOfficeLocation();
            this.preferredLanguage = userInfo.getPreferredLanguage();
            this.surname = userInfo.getSurname();
            this.userPrincipalName = userInfo.getUserPrincipalName();
//            this.infoInMicrosoft = userInfo.getInfoInMicrosoft();
            this.isActive = userInfo.getIsActive();
            this.isDelete = userInfo.getIsDelete();
            this.tennantCode = userInfo.getTennantCode();
            this.tennantName = userInfo.getTennantName();
            try {
                this.rolesString = new HashSet<>();
                if(userInfo.getRoles() != null && !userInfo.getRoles().isEmpty()){
                   this.rolesString = userInfo.getRoles().stream().map(ele -> ele.getRoleType()).collect(Collectors.toSet());
                   this.apiFeature = new HashSet<>();
                   userInfo.getRoles().stream().forEach(ele -> {
                       ele.getFeatures().stream().map(ele1 -> {
                            Set<String> result = new HashSet<>();
                            try {
                                JSONArray jsonArray = new JSONArray(ele1.getTennantCode());
                                int n = jsonArray.length();
                                for(int i=0; i<n; i++){
                                    result.add(jsonArray.getString(i));
                                }
                            }catch (Exception e1){
                                System.out.println(e1.getMessage());
                            }
                            return result;
                        }).forEach(ele1 -> {
                            this.apiFeature.addAll(ele1);
                       });
                   });
                }
            }catch (Exception e){};
//            this.tennant = userInfo.getTennant();
//            this.leader = userInfo.getLeader();
//            this.ranks = userInfo.getRanks();
//            try {
//                this.roles = new HashSet<>();
//                if(userInfo.getRoles() != null && !userInfo.getRoles().isEmpty()){
//                    userInfo.getRoles().forEach(ele -> {
//                        if(ele != null){
//                            try {
//                                Role role = new Role();
//                                BeanUtils.copyProperties(ele, role, "tennant", "created", "modified", "userInfos", "userGroups");
//                                this.roles.add(role);
//                            }catch (Exception e){}
//                        }
//                    });
//                }
//            }catch (Exception e){}
//            this.organizations = userInfo.getOrganizations();
        }

        this.authorities = authorities;
        if(this.authorities == null && userInfo != null){
            this.mySetAuthorities(userInfo);
        }
    }

    public MyUserDetail() {
    }

    public MyUserDetail(String userName, Long id, String name, String fullName, String address, String contactAddress, String identification, Instant issuseDate, String issuseOrg, String fax, String avatar, String email, String passwordEncode, Instant expiryDate, Long numberOfLoginFailed, String description, Boolean isLocked, String userTypeName, String userTypeCode, String userType, String signTypeName, String signTypeCode, String signType, String signFolder, String createdName, String createdOrgName, String createdRankName, Instant createdDate, String modifiedName, Instant modifiedDate, String idInMicrosoft, String oDataContext, String businessPhones, String displayName, String givenName, String jobTitle, String mail, String mobilePhone, String officeLocation, String preferredLanguage, String surname, String userPrincipalName, String infoInMicrosoft, Boolean isActive, Boolean isDelete, String tennantCode, String tennantName, Organization leader, Tennant tennant, UserInfo created, UserInfo modified, Set<Role> roles, Set<Rank> ranks, Set<Organization> organizations, Collection<GrantedAuthority> authorities) {
        this.userName = userName;
//        this.password = password;
        this.id = id;
        this.name = name;
        this.fullName = fullName;
        this.address = address;
        this.contactAddress = contactAddress;
        this.identification = identification;
        this.issuseDate = issuseDate;
        this.issuseOrg = issuseOrg;
        this.fax = fax;
        this.avatar = avatar;
        this.email = email;
//        this.passwordEncode = passwordEncode;
        this.expiryDate = expiryDate;
        this.numberOfLoginFailed = numberOfLoginFailed;
        this.description = description;
        this.isLocked = isLocked;
        this.userTypeName = userTypeName;
        this.userTypeCode = userTypeCode;
//        this.userType = userType;
        this.signTypeName = signTypeName;
        this.signTypeCode = signTypeCode;
        this.signType = signType;
        this.signFolder = signFolder;
        this.createdName = createdName;
        this.createdOrgName = createdOrgName;
        this.createdRankName = createdRankName;
        this.createdDate = createdDate;
        this.modifiedName = modifiedName;
        this.modifiedDate = modifiedDate;
        this.idInMicrosoft = idInMicrosoft;
        this.oDataContext = oDataContext;
        this.businessPhones = businessPhones;
        this.displayName = displayName;
        this.givenName = givenName;
        this.jobTitle = jobTitle;
        this.mail = mail;
        this.mobilePhone = mobilePhone;
        this.officeLocation = officeLocation;
        this.preferredLanguage = preferredLanguage;
        this.surname = surname;
        this.userPrincipalName = userPrincipalName;
        this.infoInMicrosoft = infoInMicrosoft;
        this.isActive = isActive;
        this.isDelete = isDelete;
        this.tennantCode = tennantCode;
        this.tennantName = tennantName;
        this.leader = leader;
        this.tennant = tennant;
        this.created = created;
        this.modified = modified;
        this.roles = roles;
        this.ranks = ranks;
        this.organizations = organizations;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

//    public UserInfo getUserInfo(){
//        return this.userInfo;
//    }

//    public void setUserInfo(UserInfo userInfo){
//        this.userInfo = userInfo;
//
//        this.setAuthorities(userInfo);
//    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

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

    public Boolean getLocked() {
        return isLocked;
    }

    public void setLocked(Boolean locked) {
        isLocked = locked;
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

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Boolean getDelete() {
        return isDelete;
    }

    public void setDelete(Boolean delete) {
        isDelete = delete;
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

    public Organization getLeader() {
        return leader;
    }

    public void setLeader(Organization leader) {
        this.leader = leader;
    }

    public Tennant getTennant() {
        return tennant;
    }

    public void setTennant(Tennant tennant) {
        this.tennant = tennant;
    }

    public UserInfo getCreated() {
        return created;
    }

    public void setCreated(UserInfo created) {
        this.created = created;
    }

    public UserInfo getModified() {
        return modified;
    }

    public void setModified(UserInfo modified) {
        this.modified = modified;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<Rank> getRanks() {
        return ranks;
    }

    public void setRanks(Set<Rank> ranks) {
        this.ranks = ranks;
    }

    public Set<Organization> getOrganizations() {
        return organizations;
    }

    public void setOrganizations(Set<Organization> organizations) {
        this.organizations = organizations;
    }

    public void setAuthorities(Collection<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    private void mySetAuthorities(UserInfo userInfo){
        this.authorities = new ArrayList<>();
        if(userInfo != null && userInfo.getRoles() != null && !userInfo.getRoles().isEmpty()){
            for (Role role : userInfo.getRoles()) {
                if(role != null && !Strings.isNullOrEmpty(role.getRoleName()))
                    authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
            }
        }
    }

    public Instant getSignInDate() {
        return signInDate;
    }

    public void setSignInDate(Instant signInDate) {
        this.signInDate = signInDate;
    }

    public Set<String> getRolesString() {
        return rolesString;
    }

    public void setRolesString(Set<String> rolesString) {
        this.rolesString = rolesString;
    }

    public Set<String> getApiFeature() {
        return apiFeature;
    }

    public void setApiFeature(Set<String> apiFeature) {
        this.apiFeature = apiFeature;
    }
}
