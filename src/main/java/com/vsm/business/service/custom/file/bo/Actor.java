package com.vsm.business.service.custom.file.bo;

public class Actor {
    protected String userId;
    protected String deptId;
    protected String roleId;
    protected String positionId;
    protected String roleName;
    protected String deptName;
    protected String positionName;
    protected String fullName;

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserId() {
        return userId;
    }

    public String getDeptId() {
        return deptId;
    }

    public String getRoleId() {
        return roleId;
    }

    public String getPositionId() {
        return positionId;
    }

    public String getRoleName() {
        return roleName;
    }

    public String getDeptName() {
        return deptName;
    }

    public String getPositionName() {
        return positionName;
    }

    public String getFullName() {
        return fullName;
    }

    @Override
    public String toString() {
        return "Actor{" +
                "userId='" + userId + '\'' +
                ", deptId='" + deptId + '\'' +
                ", roleId='" + roleId + '\'' +
                ", positionId='" + positionId + '\'' +
                ", roleName='" + roleName + '\'' +
                ", deptName='" + deptName + '\'' +
                ", positionName='" + positionName + '\'' +
                ", fullName='" + fullName + '\'' +
                '}';
    }
}
