package com.vsm.business.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.vsm.business.domain.ResultOfStep} entity.
 */
public class ResultOfStepDTO implements Serializable {

    private Long id;

    private String action;

    private String actionName;

    private String excutorName;

    private String excutorRankName;

    private String executorOrgName;

    private Instant excuteDate;

    private String tennantCode;

    private String tennantName;

    private UserInfoDTO excutor;

    private StepDataDTO stepData;

    private TennantDTO tennant;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getExcutorName() {
        return excutorName;
    }

    public void setExcutorName(String excutorName) {
        this.excutorName = excutorName;
    }

    public String getExcutorRankName() {
        return excutorRankName;
    }

    public void setExcutorRankName(String excutorRankName) {
        this.excutorRankName = excutorRankName;
    }

    public String getExecutorOrgName() {
        return executorOrgName;
    }

    public void setExecutorOrgName(String executorOrgName) {
        this.executorOrgName = executorOrgName;
    }

    public Instant getExcuteDate() {
        return excuteDate;
    }

    public void setExcuteDate(Instant excuteDate) {
        this.excuteDate = excuteDate;
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

    public UserInfoDTO getExcutor() {
        return excutor;
    }

    public void setExcutor(UserInfoDTO excutor) {
        this.excutor = excutor;
    }

    public StepDataDTO getStepData() {
        return stepData;
    }

    public void setStepData(StepDataDTO stepData) {
        this.stepData = stepData;
    }

    public TennantDTO getTennant() {
        return tennant;
    }

    public void setTennant(TennantDTO tennant) {
        this.tennant = tennant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ResultOfStepDTO)) {
            return false;
        }

        ResultOfStepDTO resultOfStepDTO = (ResultOfStepDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, resultOfStepDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ResultOfStepDTO{" +
            "id=" + getId() +
            ", action='" + getAction() + "'" +
            ", actionName='" + getActionName() + "'" +
            ", excutorName='" + getExcutorName() + "'" +
            ", excutorRankName='" + getExcutorRankName() + "'" +
            ", executorOrgName='" + getExecutorOrgName() + "'" +
            ", excuteDate='" + getExcuteDate() + "'" +
            ", tennantCode='" + getTennantCode() + "'" +
            ", tennantName='" + getTennantName() + "'" +
            ", excutor=" + getExcutor() +
            ", stepData=" + getStepData() +
            ", tennant=" + getTennant() +
            "}";
    }
}
