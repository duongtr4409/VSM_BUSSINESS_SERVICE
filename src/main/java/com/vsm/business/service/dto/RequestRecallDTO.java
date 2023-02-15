package com.vsm.business.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.vsm.business.domain.RequestRecall} entity.
 */
public class RequestRecallDTO implements Serializable {

    private Long id;

    private String recallCode;

    private String reason;

    private String result;

    private String feedBack;

    private Instant requestTime;

    private Instant processTime;

    private String createdName;

    private String createdOrgName;

    private String createdRankName;

    private Instant createdDate;

    private String modifiedName;

    private Instant modifiedate;

    private Boolean isActive;

    private Boolean isDelete;

    private UserInfoDTO recaller;

    private UserInfoDTO processer;

    private RequestDataDTO requestData;

    private StepDataDTO stepData;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRecallCode() {
        return recallCode;
    }

    public void setRecallCode(String recallCode) {
        this.recallCode = recallCode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getFeedBack() {
        return feedBack;
    }

    public void setFeedBack(String feedBack) {
        this.feedBack = feedBack;
    }

    public Instant getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(Instant requestTime) {
        this.requestTime = requestTime;
    }

    public Instant getProcessTime() {
        return processTime;
    }

    public void setProcessTime(Instant processTime) {
        this.processTime = processTime;
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

    public Instant getModifiedate() {
        return modifiedate;
    }

    public void setModifiedate(Instant modifiedate) {
        this.modifiedate = modifiedate;
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

    public UserInfoDTO getRecaller() {
        return recaller;
    }

    public void setRecaller(UserInfoDTO recaller) {
        this.recaller = recaller;
    }

    public UserInfoDTO getProcesser() {
        return processer;
    }

    public void setProcesser(UserInfoDTO processer) {
        this.processer = processer;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RequestRecallDTO)) {
            return false;
        }

        RequestRecallDTO requestRecallDTO = (RequestRecallDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, requestRecallDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RequestRecallDTO{" +
            "id=" + getId() +
            ", recallCode='" + getRecallCode() + "'" +
            ", reason='" + getReason() + "'" +
            ", result='" + getResult() + "'" +
            ", feedBack='" + getFeedBack() + "'" +
            ", requestTime='" + getRequestTime() + "'" +
            ", processTime='" + getProcessTime() + "'" +
            ", createdName='" + getCreatedName() + "'" +
            ", createdOrgName='" + getCreatedOrgName() + "'" +
            ", createdRankName='" + getCreatedRankName() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", modifiedName='" + getModifiedName() + "'" +
            ", modifiedate='" + getModifiedate() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", isDelete='" + getIsDelete() + "'" +
            ", recaller=" + getRecaller() +
            ", processer=" + getProcesser() +
            ", requestData=" + getRequestData() +
            ", stepData=" + getStepData() +
            "}";
    }
}
