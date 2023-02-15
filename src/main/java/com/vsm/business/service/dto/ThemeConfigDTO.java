package com.vsm.business.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.vsm.business.domain.ThemeConfig} entity.
 */
public class ThemeConfigDTO implements Serializable {

    private Long id;

    private String name;

    private String primary;

    private String secondary;

    private String accent;

    private String error;

    private String info;

    private String success;

    private String warning;

    private Instant createdDate;

    private Instant modifiedDate;

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

    public String getPrimary() {
        return primary;
    }

    public void setPrimary(String primary) {
        this.primary = primary;
    }

    public String getSecondary() {
        return secondary;
    }

    public void setSecondary(String secondary) {
        this.secondary = secondary;
    }

    public String getAccent() {
        return accent;
    }

    public void setAccent(String accent) {
        this.accent = accent;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getWarning() {
        return warning;
    }

    public void setWarning(String warning) {
        this.warning = warning;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ThemeConfigDTO)) {
            return false;
        }

        ThemeConfigDTO themeConfigDTO = (ThemeConfigDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, themeConfigDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ThemeConfigDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", primary='" + getPrimary() + "'" +
            ", secondary='" + getSecondary() + "'" +
            ", accent='" + getAccent() + "'" +
            ", error='" + getError() + "'" +
            ", info='" + getInfo() + "'" +
            ", success='" + getSuccess() + "'" +
            ", warning='" + getWarning() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", modifiedDate='" + getModifiedDate() + "'" +
            "}";
    }
}
