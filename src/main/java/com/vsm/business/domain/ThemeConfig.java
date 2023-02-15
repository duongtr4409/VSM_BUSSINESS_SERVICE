package com.vsm.business.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ThemeConfig.
 */
@Entity
@Table(name = "theme_config")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "themeconfig")
public class ThemeConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "vsm_primary")
    private String primary;

    @Column(name = "secondary")
    private String secondary;

    @Column(name = "accent")
    private String accent;

    @Column(name = "error")
    private String error;

    @Column(name = "info")
    private String info;

    @Column(name = "success")
    private String success;

    @Column(name = "warning")
    private String warning;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "modified_date")
    private Instant modifiedDate;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ThemeConfig id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public ThemeConfig name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrimary() {
        return this.primary;
    }

    public ThemeConfig primary(String primary) {
        this.setPrimary(primary);
        return this;
    }

    public void setPrimary(String primary) {
        this.primary = primary;
    }

    public String getSecondary() {
        return this.secondary;
    }

    public ThemeConfig secondary(String secondary) {
        this.setSecondary(secondary);
        return this;
    }

    public void setSecondary(String secondary) {
        this.secondary = secondary;
    }

    public String getAccent() {
        return this.accent;
    }

    public ThemeConfig accent(String accent) {
        this.setAccent(accent);
        return this;
    }

    public void setAccent(String accent) {
        this.accent = accent;
    }

    public String getError() {
        return this.error;
    }

    public ThemeConfig error(String error) {
        this.setError(error);
        return this;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getInfo() {
        return this.info;
    }

    public ThemeConfig info(String info) {
        this.setInfo(info);
        return this;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getSuccess() {
        return this.success;
    }

    public ThemeConfig success(String success) {
        this.setSuccess(success);
        return this;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getWarning() {
        return this.warning;
    }

    public ThemeConfig warning(String warning) {
        this.setWarning(warning);
        return this;
    }

    public void setWarning(String warning) {
        this.warning = warning;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public ThemeConfig createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getModifiedDate() {
        return this.modifiedDate;
    }

    public ThemeConfig modifiedDate(Instant modifiedDate) {
        this.setModifiedDate(modifiedDate);
        return this;
    }

    public void setModifiedDate(Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ThemeConfig)) {
            return false;
        }
        return id != null && id.equals(((ThemeConfig) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ThemeConfig{" +
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
