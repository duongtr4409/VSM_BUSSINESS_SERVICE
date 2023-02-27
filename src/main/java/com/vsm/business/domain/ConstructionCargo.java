package com.vsm.business.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;

/**
 * A ConstructionCargo.
 */
@Entity
@Table(name = "construction_cargo")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "constructioncargo")
public class ConstructionCargo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "cargo_code")
    private String cargoCode;

    @Column(name = "stt")
    private Long stt;

    @Column(name = "noi_dung_cong_viec")
    private String noi_dung_cong_viec;

    @Column(name = "quy_cach_ky_thuat")
    private String quy_cach_ky_thuat;

    @Column(name = "ma_hieu")
    private String ma_hieu;

    @Column(name = "don_vi_tinh")
    private String don_vi_tinh;

    @Column(name = "don_gia_ha_noi")
    private Double don_gia_ha_noi;

    @Column(name = "don_gia_ha_noi_str")
    private String don_gia_ha_noi_str;

    @Column(name = "don_gia_hcm")
    private Double don_gia_hcm;

    @Column(name = "don_gia_hcm_str")
    private String don_gia_hcm_str;

    @Column(name = "description")
    private String description;

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

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ConstructionCargo id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCargoCode() {
        return this.cargoCode;
    }

    public ConstructionCargo cargoCode(String cargoCode) {
        this.setCargoCode(cargoCode);
        return this;
    }

    public void setCargoCode(String cargoCode) {
        this.cargoCode = cargoCode;
    }

    public Long getStt() {
        return this.stt;
    }

    public ConstructionCargo stt(Long stt) {
        this.setStt(stt);
        return this;
    }

    public void setStt(Long stt) {
        this.stt = stt;
    }

    public String getNoi_dung_cong_viec() {
        return noi_dung_cong_viec;
    }

    public ConstructionCargo noiDungCongViec(String noiDungCongViec) {
        this.setNoi_dung_cong_viec(noiDungCongViec);
        return this;
    }

    public void setNoi_dung_cong_viec(String noiDungCongViec) {
        this.noi_dung_cong_viec = noiDungCongViec;
    }

    public String getQuy_cach_ky_thuat() {
        return quy_cach_ky_thuat;
    }

    public ConstructionCargo quyCachKyThuat(String quyCachKyThuat) {
        this.setQuy_cach_ky_thuat(quyCachKyThuat);
        return this;
    }

    public void setQuy_cach_ky_thuat(String quyCachKyThuat) {
        this.quy_cach_ky_thuat = quyCachKyThuat;
    }

    public String getMa_hieu() {
        return ma_hieu;
    }

    public ConstructionCargo maHieu(String maHieu) {
        this.setMa_hieu(maHieu);
        return this;
    }

    public void setMa_hieu(String maHieu) {
        this.ma_hieu = maHieu;
    }

    public String getDon_vi_tinh() {
        return don_vi_tinh;
    }

    public ConstructionCargo donViTinh(String donViTinh) {
        this.setDon_vi_tinh(donViTinh);
        return this;
    }

    public void setDon_vi_tinh(String donViTinh) {
        this.don_vi_tinh = donViTinh;
    }

    public Double getDon_gia_ha_noi() {
        return don_gia_ha_noi;
    }

    public ConstructionCargo donGiaHaNoi(Double donGiaHaNoi) {
        this.setDon_gia_ha_noi(donGiaHaNoi);
        return this;
    }

    public void setDon_gia_ha_noi(Double donGiaHaNoi) {
        this.don_gia_ha_noi = donGiaHaNoi;
    }

    public String getDon_gia_ha_noi_str() {
        return don_gia_ha_noi_str;
    }

    public ConstructionCargo donGiaHaNoiStr(String donGiaHaNoiStr) {
        this.setDon_gia_ha_noi_str(donGiaHaNoiStr);
        return this;
    }

    public void setDon_gia_ha_noi_str(String donGiaHaNoiStr) {
        this.don_gia_ha_noi_str = donGiaHaNoiStr;
    }

    public Double getDon_gia_hcm() {
        return don_gia_hcm;
    }

    public ConstructionCargo donGiaHCM(Double donGiaHCM) {
        this.setDon_gia_hcm(donGiaHCM);
        return this;
    }

    public void setDon_gia_hcm(Double donGiaHCM) {
        this.don_gia_hcm = donGiaHCM;
    }

    public String getDon_gia_hcm_str() {
        return don_gia_hcm_str;
    }

    public ConstructionCargo donGiaHCMStr(String donGiaHCMStr) {
        this.setDon_gia_hcm_str(donGiaHCMStr);
        return this;
    }

    public void setDon_gia_hcm_str(String donGiaHCMStr) {
        this.don_gia_hcm_str = donGiaHCMStr;
    }

    public String getDescription() {
        return this.description;
    }

    public ConstructionCargo description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedName() {
        return this.createdName;
    }

    public ConstructionCargo createdName(String createdName) {
        this.setCreatedName(createdName);
        return this;
    }

    public void setCreatedName(String createdName) {
        this.createdName = createdName;
    }

    public String getCreatedOrgName() {
        return this.createdOrgName;
    }

    public ConstructionCargo createdOrgName(String createdOrgName) {
        this.setCreatedOrgName(createdOrgName);
        return this;
    }

    public void setCreatedOrgName(String createdOrgName) {
        this.createdOrgName = createdOrgName;
    }

    public String getCreatedRankName() {
        return this.createdRankName;
    }

    public ConstructionCargo createdRankName(String createdRankName) {
        this.setCreatedRankName(createdRankName);
        return this;
    }

    public void setCreatedRankName(String createdRankName) {
        this.createdRankName = createdRankName;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public ConstructionCargo createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedName() {
        return this.modifiedName;
    }

    public ConstructionCargo modifiedName(String modifiedName) {
        this.setModifiedName(modifiedName);
        return this;
    }

    public void setModifiedName(String modifiedName) {
        this.modifiedName = modifiedName;
    }

    public Instant getModifiedDate() {
        return this.modifiedDate;
    }

    public ConstructionCargo modifiedDate(Instant modifiedDate) {
        this.setModifiedDate(modifiedDate);
        return this;
    }

    public void setModifiedDate(Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public ConstructionCargo isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getIsDelete() {
        return this.isDelete;
    }

    public ConstructionCargo isDelete(Boolean isDelete) {
        this.setIsDelete(isDelete);
        return this;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConstructionCargo)) {
            return false;
        }
        return id != null && id.equals(((ConstructionCargo) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ConstructionCargo{" +
            "id=" + getId() +
            ", cargoCode='" + getCargoCode() + "'" +
            ", stt=" + getStt() +
            ", noiDungCongViec='" + getNoi_dung_cong_viec() + "'" +
            ", quyCachKyThuat='" + getQuy_cach_ky_thuat() + "'" +
            ", maHieu='" + getMa_hieu() + "'" +
            ", donViTinh='" + getDon_vi_tinh() + "'" +
            ", donGiaHaNoi=" + getDon_gia_ha_noi() +
            ", donGiaHaNoiStr='" + getDon_gia_ha_noi_str() + "'" +
            ", donGiaHCM=" + getDon_gia_hcm() +
            ", donGiaHCMStr='" + getDon_gia_hcm_str() + "'" +
            ", description='" + getDescription() + "'" +
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
