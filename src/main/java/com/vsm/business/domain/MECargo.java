package com.vsm.business.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;

/**
 * A MECargo.
 */
@Entity
@Table(name = "me_cargo")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "mecargo")
public class MECargo implements Serializable {

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

    @Column(name = "don_gia_vat_tu_vat_lieu")
    private Double don_gia_vat_tu_vat_lieu;

    @Column(name = "don_gia_vat_tu_vat_lieu_str")
    private String don_gia_vat_tu_vat_lieu_str;

    @Column(name = "don_gia_nhan_cong")
    private Double don_gia_nhan_cong;

    @Column(name = "don_gia_nhan_cong_str")
    private String don_gia_nhan_cong_str;

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

    public MECargo id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCargoCode() {
        return this.cargoCode;
    }

    public MECargo cargoCode(String cargoCode) {
        this.setCargoCode(cargoCode);
        return this;
    }

    public void setCargoCode(String cargoCode) {
        this.cargoCode = cargoCode;
    }

    public Long getStt() {
        return this.stt;
    }

    public MECargo stt(Long stt) {
        this.setStt(stt);
        return this;
    }

    public void setStt(Long stt) {
        this.stt = stt;
    }

    public String getNoi_dung_cong_viec() {
        return noi_dung_cong_viec;
    }

    public MECargo noiDungCongViec(String noiDungCongViec) {
        this.setNoiDungCongViec(noiDungCongViec);
        return this;
    }

    public void setNoiDungCongViec(String noiDungCongViec) {
        this.noi_dung_cong_viec = noiDungCongViec;
    }

    public String getQuy_cach_ky_thuat() {
        return quy_cach_ky_thuat;
    }

    public MECargo quyCachKyThuat(String quyCachKyThuat) {
        this.setQuyCachKyThuat(quyCachKyThuat);
        return this;
    }

    public void setQuyCachKyThuat(String quyCachKyThuat) {
        this.quy_cach_ky_thuat = quyCachKyThuat;
    }

    public String getMa_hieu() {
        return ma_hieu;
    }

    public MECargo maHieu(String maHieu) {
        this.setMaHieu(maHieu);
        return this;
    }

    public void setMaHieu(String maHieu) {
        this.ma_hieu = maHieu;
    }

    public String getDon_vi_tinh() {
        return don_vi_tinh;
    }

    public MECargo donViTinh(String donViTinh) {
        this.setDonViTinh(donViTinh);
        return this;
    }

    public void setDonViTinh(String donViTinh) {
        this.don_vi_tinh = donViTinh;
    }

    public Double getDon_gia_vat_tu_vat_lieu() {
        return don_gia_vat_tu_vat_lieu;
    }

    public MECargo donGiaVatTuVatLieu(Double donGiaVatTuVatLieu) {
        this.setDonGiaVatTuVatLieu(donGiaVatTuVatLieu);
        return this;
    }

    public void setDonGiaVatTuVatLieu(Double donGiaVatTuVatLieu) {
        this.don_gia_vat_tu_vat_lieu = donGiaVatTuVatLieu;
    }

    public String getDon_gia_vat_tu_vat_lieu_str() {
        return don_gia_vat_tu_vat_lieu_str;
    }

    public MECargo donGiaVatTuVatLieuStr(String donGiaVatTuVatLieuStr) {
        this.setDonGiaVatTuVatLieuStr(donGiaVatTuVatLieuStr);
        return this;
    }

    public void setDonGiaVatTuVatLieuStr(String donGiaVatTuVatLieuStr) {
        this.don_gia_vat_tu_vat_lieu_str = donGiaVatTuVatLieuStr;
    }

    public Double getDon_gia_nhan_cong() {
        return don_gia_nhan_cong;
    }

    public MECargo donGiaNhanCong(Double donGiaNhanCong) {
        this.setDonGiaNhanCong(donGiaNhanCong);
        return this;
    }

    public void setDonGiaNhanCong(Double donGiaNhanCong) {
        this.don_gia_nhan_cong = donGiaNhanCong;
    }

    public String getDon_gia_nhan_cong_str() {
        return don_gia_nhan_cong_str;
    }

    public MECargo donGiaNhanCongStr(String donGiaNhanCongStr) {
        this.setDonGiaNhanCongStr(donGiaNhanCongStr);
        return this;
    }

    public void setDonGiaNhanCongStr(String donGiaNhanCongStr) {
        this.don_gia_nhan_cong_str = donGiaNhanCongStr;
    }

    public String getDescription() {
        return this.description;
    }

    public MECargo description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedName() {
        return this.createdName;
    }

    public MECargo createdName(String createdName) {
        this.setCreatedName(createdName);
        return this;
    }

    public void setCreatedName(String createdName) {
        this.createdName = createdName;
    }

    public String getCreatedOrgName() {
        return this.createdOrgName;
    }

    public MECargo createdOrgName(String createdOrgName) {
        this.setCreatedOrgName(createdOrgName);
        return this;
    }

    public void setCreatedOrgName(String createdOrgName) {
        this.createdOrgName = createdOrgName;
    }

    public String getCreatedRankName() {
        return this.createdRankName;
    }

    public MECargo createdRankName(String createdRankName) {
        this.setCreatedRankName(createdRankName);
        return this;
    }

    public void setCreatedRankName(String createdRankName) {
        this.createdRankName = createdRankName;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public MECargo createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedName() {
        return this.modifiedName;
    }

    public MECargo modifiedName(String modifiedName) {
        this.setModifiedName(modifiedName);
        return this;
    }

    public void setModifiedName(String modifiedName) {
        this.modifiedName = modifiedName;
    }

    public Instant getModifiedDate() {
        return this.modifiedDate;
    }

    public MECargo modifiedDate(Instant modifiedDate) {
        this.setModifiedDate(modifiedDate);
        return this;
    }

    public void setModifiedDate(Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public MECargo isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getIsDelete() {
        return this.isDelete;
    }

    public MECargo isDelete(Boolean isDelete) {
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
        if (!(o instanceof MECargo)) {
            return false;
        }
        return id != null && id.equals(((MECargo) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MECargo{" +
            "id=" + getId() +
            ", cargoCode='" + getCargoCode() + "'" +
            ", stt=" + getStt() +
            ", noiDungCongViec='" + getNoi_dung_cong_viec() + "'" +
            ", quyCachKyThuat='" + getQuy_cach_ky_thuat() + "'" +
            ", maHieu='" + getMa_hieu() + "'" +
            ", donViTinh='" + getDon_vi_tinh() + "'" +
            ", donGiaVatTuVatLieu=" + getDon_gia_vat_tu_vat_lieu() +
            ", donGiaVatTuVatLieuStr='" + getDon_gia_vat_tu_vat_lieu_str() + "'" +
            ", donGiaNhanCong=" + getDon_gia_nhan_cong() +
            ", donGiaNhanCongStr='" + getDon_gia_nhan_cong_str() + "'" +
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
