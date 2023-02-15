package com.vsm.business.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.vsm.business.domain.MallAndStall} entity.
 */
public class MallAndStallDTO implements Serializable {

    private Long id;

    private Long stt;

    private String companyCode;

    private String profitCenter;

    private String profitCenterName;

    private String businessEntity;

    private String maKhachHang;

    private String tenKhachHang;

    private String tenNVKD;

    private String soLo;

    private String tenGianHang;

    private Double dienTich;

    private String maHopDong;

    private Instant ngayKetThucHD;

    private Boolean isSyncFromSAP;

    private Instant timeSync;

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

    public Long getStt() {
        return stt;
    }

    public void setStt(Long stt) {
        this.stt = stt;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getProfitCenter() {
        return profitCenter;
    }

    public void setProfitCenter(String profitCenter) {
        this.profitCenter = profitCenter;
    }

    public String getProfitCenterName() {
        return profitCenterName;
    }

    public void setProfitCenterName(String profitCenterName) {
        this.profitCenterName = profitCenterName;
    }

    public String getBusinessEntity() {
        return businessEntity;
    }

    public void setBusinessEntity(String businessEntity) {
        this.businessEntity = businessEntity;
    }

    public String getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(String maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }

    public String getTenNVKD() {
        return tenNVKD;
    }

    public void setTenNVKD(String tenNVKD) {
        this.tenNVKD = tenNVKD;
    }

    public String getSoLo() {
        return soLo;
    }

    public void setSoLo(String soLo) {
        this.soLo = soLo;
    }

    public String getTenGianHang() {
        return tenGianHang;
    }

    public void setTenGianHang(String tenGianHang) {
        this.tenGianHang = tenGianHang;
    }

    public Double getDienTich() {
        return dienTich;
    }

    public void setDienTich(Double dienTich) {
        this.dienTich = dienTich;
    }

    public String getMaHopDong() {
        return maHopDong;
    }

    public void setMaHopDong(String maHopDong) {
        this.maHopDong = maHopDong;
    }

    public Instant getNgayKetThucHD() {
        return ngayKetThucHD;
    }

    public void setNgayKetThucHD(Instant ngayKetThucHD) {
        this.ngayKetThucHD = ngayKetThucHD;
    }

    public Boolean getIsSyncFromSAP() {
        return isSyncFromSAP;
    }

    public void setIsSyncFromSAP(Boolean isSyncFromSAP) {
        this.isSyncFromSAP = isSyncFromSAP;
    }

    public Instant getTimeSync() {
        return timeSync;
    }

    public void setTimeSync(Instant timeSync) {
        this.timeSync = timeSync;
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
        if (!(o instanceof MallAndStallDTO)) {
            return false;
        }

        MallAndStallDTO mallAndStallDTO = (MallAndStallDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, mallAndStallDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MallAndStallDTO{" +
            "id=" + getId() +
            ", stt=" + getStt() +
            ", companyCode='" + getCompanyCode() + "'" +
            ", profitCenter='" + getProfitCenter() + "'" +
            ", profitCenterName='" + getProfitCenterName() + "'" +
            ", businessEntity='" + getBusinessEntity() + "'" +
            ", maKhachHang='" + getMaKhachHang() + "'" +
            ", tenKhachHang='" + getTenKhachHang() + "'" +
            ", tenNVKD='" + getTenNVKD() + "'" +
            ", soLo='" + getSoLo() + "'" +
            ", tenGianHang='" + getTenGianHang() + "'" +
            ", dienTich=" + getDienTich() +
            ", maHopDong='" + getMaHopDong() + "'" +
            ", ngayKetThucHD='" + getNgayKetThucHD() + "'" +
            ", isSyncFromSAP='" + getIsSyncFromSAP() + "'" +
            ", timeSync='" + getTimeSync() + "'" +
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
