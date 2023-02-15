package com.vsm.business.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A StatusTransferHandle.
 */
@Entity
@Table(name = "status_transfer_handle")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "statustransferhandle")
public class StatusTransferHandle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "status_name")
    private String statusName;

    @Column(name = "status_code")
    private String statusCode;

    @Column(name = "created_name")
    private String createdName;

    @Column(name = "created_org_name")
    private String createdOrgName;

    @Column(name = "created_rank_name")
    private String createdRankName;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "modified_name")
    private String modifiedName;

    @Column(name = "modified_date")
    private Instant modifiedDate;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "is_delete")
    private Boolean isDelete;

    @OneToMany(mappedBy = "statusTransferHandle")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "statusTransferHandle", "organization", "dispatchBook", "stepData", "transfer", "creater", "modifier", "receiversHandles",
        },
        allowSetters = true
    )
    private Set<TransferHandle> transferHandles = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public StatusTransferHandle id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatusName() {
        return this.statusName;
    }

    public StatusTransferHandle statusName(String statusName) {
        this.setStatusName(statusName);
        return this;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getStatusCode() {
        return this.statusCode;
    }

    public StatusTransferHandle statusCode(String statusCode) {
        this.setStatusCode(statusCode);
        return this;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getCreatedName() {
        return this.createdName;
    }

    public StatusTransferHandle createdName(String createdName) {
        this.setCreatedName(createdName);
        return this;
    }

    public void setCreatedName(String createdName) {
        this.createdName = createdName;
    }

    public String getCreatedOrgName() {
        return this.createdOrgName;
    }

    public StatusTransferHandle createdOrgName(String createdOrgName) {
        this.setCreatedOrgName(createdOrgName);
        return this;
    }

    public void setCreatedOrgName(String createdOrgName) {
        this.createdOrgName = createdOrgName;
    }

    public String getCreatedRankName() {
        return this.createdRankName;
    }

    public StatusTransferHandle createdRankName(String createdRankName) {
        this.setCreatedRankName(createdRankName);
        return this;
    }

    public void setCreatedRankName(String createdRankName) {
        this.createdRankName = createdRankName;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public StatusTransferHandle createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedName() {
        return this.modifiedName;
    }

    public StatusTransferHandle modifiedName(String modifiedName) {
        this.setModifiedName(modifiedName);
        return this;
    }

    public void setModifiedName(String modifiedName) {
        this.modifiedName = modifiedName;
    }

    public Instant getModifiedDate() {
        return this.modifiedDate;
    }

    public StatusTransferHandle modifiedDate(Instant modifiedDate) {
        this.setModifiedDate(modifiedDate);
        return this;
    }

    public void setModifiedDate(Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public StatusTransferHandle isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getIsDelete() {
        return this.isDelete;
    }

    public StatusTransferHandle isDelete(Boolean isDelete) {
        this.setIsDelete(isDelete);
        return this;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public Set<TransferHandle> getTransferHandles() {
        return this.transferHandles;
    }

    public void setTransferHandles(Set<TransferHandle> transferHandles) {
        if (this.transferHandles != null) {
            this.transferHandles.forEach(i -> i.setStatusTransferHandle(null));
        }
        if (transferHandles != null) {
            transferHandles.forEach(i -> i.setStatusTransferHandle(this));
        }
        this.transferHandles = transferHandles;
    }

    public StatusTransferHandle transferHandles(Set<TransferHandle> transferHandles) {
        this.setTransferHandles(transferHandles);
        return this;
    }

    public StatusTransferHandle addTransferHandle(TransferHandle transferHandle) {
        this.transferHandles.add(transferHandle);
        transferHandle.setStatusTransferHandle(this);
        return this;
    }

    public StatusTransferHandle removeTransferHandle(TransferHandle transferHandle) {
        this.transferHandles.remove(transferHandle);
        transferHandle.setStatusTransferHandle(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StatusTransferHandle)) {
            return false;
        }
        return id != null && id.equals(((StatusTransferHandle) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StatusTransferHandle{" +
            "id=" + getId() +
            ", statusName='" + getStatusName() + "'" +
            ", statusCode='" + getStatusCode() + "'" +
            ", createdName='" + getCreatedName() + "'" +
            ", createdOrgName='" + getCreatedOrgName() + "'" +
            ", createdRankName='" + getCreatedRankName() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", modifiedName='" + getModifiedName() + "'" +
            ", modifiedDate='" + getModifiedDate() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", isDelete='" + getIsDelete() + "'" +
            "}";
    }
}
