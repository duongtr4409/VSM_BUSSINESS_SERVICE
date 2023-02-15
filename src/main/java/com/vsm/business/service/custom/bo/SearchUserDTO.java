package com.vsm.business.service.custom.bo;

public class SearchUserDTO {
    private Long organizationId;
    private String fullName;

    public SearchUserDTO() {
    }

    public SearchUserDTO(Long organizationId, String fullName) {
        this.organizationId = organizationId;
        this.fullName = fullName;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public String toString() {
        return "SearchUserDTO{" +
            "organizationId=" + organizationId +
            ", fullName='" + fullName + '\'' +
            '}';
    }
}
