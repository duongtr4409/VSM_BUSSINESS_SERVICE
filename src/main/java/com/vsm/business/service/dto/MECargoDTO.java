package com.vsm.business.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.vsm.business.domain.MECargo} entity.
 */
public class MECargoDTO implements Serializable {

    private Long id;

    private String cargoCode;

    private Long stt;

    private String noi_dung_cong_viec;

    private String quy_cach_ky_thuat;

    private String ma_hieu;

    private String don_vi_tinh;

    private Double don_gia_vat_tu_vat_lieu;

    private String don_gia_vat_tu_vat_lieu_str;

    private Double don_gia_nhan_cong;

    private String don_gia_nhan_cong_str;

    private String description;

    private String createdName;

    private String createdOrgName;

    private String createdRankName;

    private Instant createdDate;

    private String modifiedName;

    private Instant modifiedDate;

    private Boolean isActive;

    private Boolean isDelete;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCargoCode() {
        return cargoCode;
    }

    public void setCargoCode(String cargoCode) {
        this.cargoCode = cargoCode;
    }

    public Long getStt() {
        return stt;
    }

    public void setStt(Long stt) {
        this.stt = stt;
    }

    public String getNoi_dung_cong_viec() {
        return noi_dung_cong_viec;
    }

    public void setNoi_dung_cong_viec(String noi_dung_cong_viec) {
        this.noi_dung_cong_viec = noi_dung_cong_viec;
    }

    public String getQuy_cach_ky_thuat() {
        return quy_cach_ky_thuat;
    }

    public void setQuy_cach_ky_thuat(String quy_cach_ky_thuat) {
        this.quy_cach_ky_thuat = quy_cach_ky_thuat;
    }

    public String getMa_hieu() {
        return ma_hieu;
    }

    public void setMa_hieu(String ma_hieu) {
        this.ma_hieu = ma_hieu;
    }

    public String getDon_vi_tinh() {
        return don_vi_tinh;
    }

    public void setDon_vi_tinh(String don_vi_tinh) {
        this.don_vi_tinh = don_vi_tinh;
    }

    public Double getDon_gia_vat_tu_vat_lieu() {
        return don_gia_vat_tu_vat_lieu;
    }

    public void setDon_gia_vat_tu_vat_lieu(Double don_gia_vat_tu_vat_lieu) {
        this.don_gia_vat_tu_vat_lieu = don_gia_vat_tu_vat_lieu;
    }

    public String getDon_gia_vat_tu_vat_lieu_str() {
        return don_gia_vat_tu_vat_lieu_str;
    }

    public void setDon_gia_vat_tu_vat_lieu_str(String don_gia_vat_tu_vat_lieu_str) {
        this.don_gia_vat_tu_vat_lieu_str = don_gia_vat_tu_vat_lieu_str;
    }

    public Double getDon_gia_nhan_cong() {
        return don_gia_nhan_cong;
    }

    public void setDon_gia_nhan_cong(Double don_gia_nhan_cong) {
        this.don_gia_nhan_cong = don_gia_nhan_cong;
    }

    public String getDon_gia_nhan_cong_str() {
        return don_gia_nhan_cong_str;
    }

    public void setDon_gia_nhan_cong_str(String don_gia_nhan_cong_str) {
        this.don_gia_nhan_cong_str = don_gia_nhan_cong_str;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Instant getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MECargoDTO)) {
            return false;
        }

        MECargoDTO mECargoDTO = (MECargoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, mECargoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MECargoDTO{" +
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
