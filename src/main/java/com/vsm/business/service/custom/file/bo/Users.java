package com.vsm.business.service.custom.file.bo;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Clob;
import java.util.Date;
import java.util.Objects;

public class Users implements Serializable {

    private String id;
    private String userName;
    private String fullName;
    private String password;
    private String mobile;
    private String email;
    private Clob photo;
    private String caNumber;
    private BigDecimal caType;
    private String caSerial;
    private String positionId;
    private String deptId;
    private Date birthDate;
    private BigDecimal gender;
    private String idCard;
    private String staffCardNumber;
    private BigDecimal passwordChanged;
    private Date passwordExpireDate;
    private Long isDelete;
    private String creatorId;
    private String creatorName;
    private Date createTime;
    private String updatorId;
    private String updatorName;
    private Date updateTime;
    private String status;
    private String tenantCode;

    public Users() {
    }

    public Users(String id, String userName, String fullName, String password, String mobile, String email, Clob photo, String caNumber, BigDecimal caType, String caSerial, String positionId, String deptId, Date birthDate, BigDecimal gender, String idCard, String staffCardNumber, BigDecimal passwordChanged, Date passwordExpireDate, Long isDelete, String creatorId, String creatorName, Date createTime, String updatorId, String updatorName, Date updateTime, String status, String tenantCode) {
        this.id = id;
        this.userName = userName;
        this.fullName = fullName;
        this.password = password;
        this.mobile = mobile;
        this.email = email;
        this.photo = photo;
        this.caNumber = caNumber;
        this.caType = caType;
        this.caSerial = caSerial;
        this.positionId = positionId;
        this.deptId = deptId;
        this.birthDate = birthDate;
        this.gender = gender;
        this.idCard = idCard;
        this.staffCardNumber = staffCardNumber;
        this.passwordChanged = passwordChanged;
        this.passwordExpireDate = passwordExpireDate;
        this.isDelete = isDelete;
        this.creatorId = creatorId;
        this.creatorName = creatorName;
        this.createTime = createTime;
        this.updatorId = updatorId;
        this.updatorName = updatorName;
        this.updateTime = updateTime;
        this.status = status;
        this.tenantCode = tenantCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Users users = (Users) o;
        return Objects.equals(id, users.id) && Objects.equals(userName, users.userName) && Objects.equals(fullName, users.fullName) && Objects.equals(password, users.password) && Objects.equals(mobile, users.mobile) && Objects.equals(email, users.email) && Objects.equals(photo, users.photo) && Objects.equals(caNumber, users.caNumber) && Objects.equals(caType, users.caType) && Objects.equals(caSerial, users.caSerial) && Objects.equals(positionId, users.positionId) && Objects.equals(deptId, users.deptId) && Objects.equals(birthDate, users.birthDate) && Objects.equals(gender, users.gender) && Objects.equals(idCard, users.idCard) && Objects.equals(staffCardNumber, users.staffCardNumber) && Objects.equals(passwordChanged, users.passwordChanged) && Objects.equals(passwordExpireDate, users.passwordExpireDate) && Objects.equals(isDelete, users.isDelete) && Objects.equals(creatorId, users.creatorId) && Objects.equals(creatorName, users.creatorName) && Objects.equals(createTime, users.createTime) && Objects.equals(updatorId, users.updatorId) && Objects.equals(updatorName, users.updatorName) && Objects.equals(updateTime, users.updateTime) && Objects.equals(status, users.status) && Objects.equals(tenantCode, users.tenantCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, fullName, password, mobile, email, photo, caNumber, caType, caSerial, positionId, deptId, birthDate, gender, idCard, staffCardNumber, passwordChanged, passwordExpireDate, isDelete, creatorId, creatorName, createTime, updatorId, updatorName, updateTime, status, tenantCode);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Clob getPhoto() {
        return photo;
    }

    public void setPhoto(Clob photo) {
        this.photo = photo;
    }

    public String getCaNumber() {
        return caNumber;
    }

    public void setCaNumber(String caNumber) {
        this.caNumber = caNumber;
    }

    public BigDecimal getCaType() {
        return caType;
    }

    public void setCaType(BigDecimal caType) {
        this.caType = caType;
    }

    public String getCaSerial() {
        return caSerial;
    }

    public void setCaSerial(String caSerial) {
        this.caSerial = caSerial;
    }

    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public BigDecimal getGender() {
        return gender;
    }

    public void setGender(BigDecimal gender) {
        this.gender = gender;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getStaffCardNumber() {
        return staffCardNumber;
    }

    public void setStaffCardNumber(String staffCardNumber) {
        this.staffCardNumber = staffCardNumber;
    }

    public BigDecimal getPasswordChanged() {
        return passwordChanged;
    }

    public void setPasswordChanged(BigDecimal passwordChanged) {
        this.passwordChanged = passwordChanged;
    }

    public Date getPasswordExpireDate() {
        return passwordExpireDate;
    }

    public void setPasswordExpireDate(Date passwordExpireDate) {
        this.passwordExpireDate = passwordExpireDate;
    }

    public Long getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Long isDelete) {
        this.isDelete = isDelete;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdatorId() {
        return updatorId;
    }

    public void setUpdatorId(String updatorId) {
        this.updatorId = updatorId;
    }

    public String getUpdatorName() {
        return updatorName;
    }

    public void setUpdatorName(String updatorName) {
        this.updatorName = updatorName;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    @Override
    public String toString() {
        return "Users{" +
            "id='" + id + '\'' +
            ", userName='" + userName + '\'' +
            ", fullName='" + fullName + '\'' +
            ", password='" + password + '\'' +
            ", mobile='" + mobile + '\'' +
            ", email='" + email + '\'' +
            ", photo=" + photo +
            ", caNumber='" + caNumber + '\'' +
            ", caType=" + caType +
            ", caSerial='" + caSerial + '\'' +
            ", positionId='" + positionId + '\'' +
            ", deptId='" + deptId + '\'' +
            ", birthDate=" + birthDate +
            ", gender=" + gender +
            ", idCard='" + idCard + '\'' +
            ", staffCardNumber='" + staffCardNumber + '\'' +
            ", passwordChanged=" + passwordChanged +
            ", passwordExpireDate=" + passwordExpireDate +
            ", isDelete=" + isDelete +
            ", creatorId='" + creatorId + '\'' +
            ", creatorName='" + creatorName + '\'' +
            ", createTime=" + createTime +
            ", updatorId='" + updatorId + '\'' +
            ", updatorName='" + updatorName + '\'' +
            ", updateTime=" + updateTime +
            ", status='" + status + '\'' +
            ", tenantCode='" + tenantCode + '\'' +
            '}';
    }
}
