package com.vsm.business.service.custom.dashboard.bo;

import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.List;

public class DashboardDto {
    @NotNull
    private Long requestGroupId;
    private Long userId;

    private Long organizationId;
    private Instant startDate;
    private Instant endDate;
    private List<Long> organizationIds;

    public DashboardDto() {
    }

    public DashboardDto(Long requestGroupId, Long userId, Long organizationId, Instant startDate, Instant endDate, List<Long> organizationIds) {
        this.requestGroupId = requestGroupId;
        this.userId = userId;
        this.organizationId = organizationId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.organizationIds = organizationIds;
    }

    public Long getRequestGroupId() {
        return requestGroupId;
    }

    public void setRequestGroupId(Long requestGroupId) {
        this.requestGroupId = requestGroupId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public List<Long> getOrganizationIds() {
        return organizationIds;
    }

    public void setOrganizationIds(List<Long> organizationIds) {
        this.organizationIds = organizationIds;
    }

    @Override
    public String toString() {
        return "DashboardDto{" +
            "requestGroupId=" + requestGroupId +
            ", userId=" + userId +
            ", organizationId=" + organizationId +
            ", startDate=" + startDate +
            ", endDate=" + endDate +
            ", organizationIds=" + organizationIds +
            '}';
    }
}
