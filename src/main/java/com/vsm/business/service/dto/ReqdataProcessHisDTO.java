package com.vsm.business.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.vsm.business.domain.ReqdataProcessHis} entity.
 */
public class ReqdataProcessHisDTO implements Serializable {

    private Long id;

    private String processerName;

    private String organizationName;

    private String rankName;

    private Instant processDate;

    private String description;

    private String status;

    private Instant createDate;

    private Boolean isChild;

    private Boolean isShowCustomer;

    private Long tennantId;

    private String tennantCode;

    private String tennantName;

    private RequestDataDTO requestData;

    private StepDataDTO stepData;

    private UserInfoDTO processer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProcesserName() {
        return processerName;
    }

    public void setProcesserName(String processerName) {
        this.processerName = processerName;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getRankName() {
        return rankName;
    }

    public void setRankName(String rankName) {
        this.rankName = rankName;
    }

    public Instant getProcessDate() {
        return processDate;
    }

    public void setProcessDate(Instant processDate) {
        this.processDate = processDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Instant createDate) {
        this.createDate = createDate;
    }

    public Boolean getIsChild() {
        return isChild;
    }

    public void setIsChild(Boolean isChild) {
        this.isChild = isChild;
    }

    public Boolean getIsShowCustomer() {
        return isShowCustomer;
    }

    public void setIsShowCustomer(Boolean isShowCustomer) {
        this.isShowCustomer = isShowCustomer;
    }

    public Long getTennantId() {
        return tennantId;
    }

    public void setTennantId(Long tennantId) {
        this.tennantId = tennantId;
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

    public RequestDataDTO getRequestData() {
        return requestData;
    }

    public void setRequestData(RequestDataDTO requestData) {
        this.requestData = requestData;
    }

    public StepDataDTO getStepData() {
        return stepData;
    }

    public void setStepData(StepDataDTO stepData) {
        this.stepData = stepData;
    }

    public UserInfoDTO getProcesser() {
        return processer;
    }

    public void setProcesser(UserInfoDTO processer) {
        this.processer = processer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReqdataProcessHisDTO)) {
            return false;
        }

        ReqdataProcessHisDTO reqdataProcessHisDTO = (ReqdataProcessHisDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, reqdataProcessHisDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReqdataProcessHisDTO{" +
            "id=" + getId() +
            ", processerName='" + getProcesserName() + "'" +
            ", organizationName='" + getOrganizationName() + "'" +
            ", rankName='" + getRankName() + "'" +
            ", processDate='" + getProcessDate() + "'" +
            ", description='" + getDescription() + "'" +
            ", status='" + getStatus() + "'" +
            ", createDate='" + getCreateDate() + "'" +
            ", isChild='" + getIsChild() + "'" +
            ", isShowCustomer='" + getIsShowCustomer() + "'" +
            ", tennantId=" + getTennantId() +
            ", tennantCode='" + getTennantCode() + "'" +
            ", tennantName='" + getTennantName() + "'" +
            ", requestData=" + getRequestData() +
            ", stepData=" + getStepData() +
            ", processer=" + getProcesser() +
            "}";
    }
}
