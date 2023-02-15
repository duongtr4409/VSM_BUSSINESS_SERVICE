package com.vsm.business.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.vsm.business.domain.CentralizedShopping} entity.
 */
public class CentralizedShoppingDTO implements Serializable {

    private Long id;

    private Long stt;

    private Long bangSo;

    private String level1;

    private String tenNhomLevel1;

    private String level2;

    private String tenNhomLevel2;

    private String level3;

    private String tenNhomLevel3;

    private String level4;

    private String tenNhomLevel4;

    private String maChungPnL;

    private String shortName;

    private String dVTSAP;

    private Long doDaiShortName;

    private String fullName;

    private String pLSuDung;

    private String pICYeuCauTaoMa;

    private String cBLDPnLPheDuyet;

    private String noteKhoaMa;

    private Instant ngayTaoMa;

    private String ghiChuKhac;

    private String iDGuiITXuLy;

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

    public Long getBangSo() {
        return bangSo;
    }

    public void setBangSo(Long bangSo) {
        this.bangSo = bangSo;
    }

    public String getLevel1() {
        return level1;
    }

    public void setLevel1(String level1) {
        this.level1 = level1;
    }

    public String getTenNhomLevel1() {
        return tenNhomLevel1;
    }

    public void setTenNhomLevel1(String tenNhomLevel1) {
        this.tenNhomLevel1 = tenNhomLevel1;
    }

    public String getLevel2() {
        return level2;
    }

    public void setLevel2(String level2) {
        this.level2 = level2;
    }

    public String getTenNhomLevel2() {
        return tenNhomLevel2;
    }

    public void setTenNhomLevel2(String tenNhomLevel2) {
        this.tenNhomLevel2 = tenNhomLevel2;
    }

    public String getLevel3() {
        return level3;
    }

    public void setLevel3(String level3) {
        this.level3 = level3;
    }

    public String getTenNhomLevel3() {
        return tenNhomLevel3;
    }

    public void setTenNhomLevel3(String tenNhomLevel3) {
        this.tenNhomLevel3 = tenNhomLevel3;
    }

    public String getLevel4() {
        return level4;
    }

    public void setLevel4(String level4) {
        this.level4 = level4;
    }

    public String getTenNhomLevel4() {
        return tenNhomLevel4;
    }

    public void setTenNhomLevel4(String tenNhomLevel4) {
        this.tenNhomLevel4 = tenNhomLevel4;
    }

    public String getMaChungPnL() {
        return maChungPnL;
    }

    public void setMaChungPnL(String maChungPnL) {
        this.maChungPnL = maChungPnL;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getdVTSAP() {
        return dVTSAP;
    }

    public void setdVTSAP(String dVTSAP) {
        this.dVTSAP = dVTSAP;
    }

    public Long getDoDaiShortName() {
        return doDaiShortName;
    }

    public void setDoDaiShortName(Long doDaiShortName) {
        this.doDaiShortName = doDaiShortName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getpLSuDung() {
        return pLSuDung;
    }

    public void setpLSuDung(String pLSuDung) {
        this.pLSuDung = pLSuDung;
    }

    public String getpICYeuCauTaoMa() {
        return pICYeuCauTaoMa;
    }

    public void setpICYeuCauTaoMa(String pICYeuCauTaoMa) {
        this.pICYeuCauTaoMa = pICYeuCauTaoMa;
    }

    public String getcBLDPnLPheDuyet() {
        return cBLDPnLPheDuyet;
    }

    public void setcBLDPnLPheDuyet(String cBLDPnLPheDuyet) {
        this.cBLDPnLPheDuyet = cBLDPnLPheDuyet;
    }

    public String getNoteKhoaMa() {
        return noteKhoaMa;
    }

    public void setNoteKhoaMa(String noteKhoaMa) {
        this.noteKhoaMa = noteKhoaMa;
    }

    public Instant getNgayTaoMa() {
        return ngayTaoMa;
    }

    public void setNgayTaoMa(Instant ngayTaoMa) {
        this.ngayTaoMa = ngayTaoMa;
    }

    public String getGhiChuKhac() {
        return ghiChuKhac;
    }

    public void setGhiChuKhac(String ghiChuKhac) {
        this.ghiChuKhac = ghiChuKhac;
    }

    public String getiDGuiITXuLy() {
        return iDGuiITXuLy;
    }

    public void setiDGuiITXuLy(String iDGuiITXuLy) {
        this.iDGuiITXuLy = iDGuiITXuLy;
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
        if (!(o instanceof CentralizedShoppingDTO)) {
            return false;
        }

        CentralizedShoppingDTO centralizedShoppingDTO = (CentralizedShoppingDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, centralizedShoppingDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CentralizedShoppingDTO{" +
            "id=" + getId() +
            ", stt=" + getStt() +
            ", bangSo=" + getBangSo() +
            ", level1='" + getLevel1() + "'" +
            ", tenNhomLevel1='" + getTenNhomLevel1() + "'" +
            ", level2='" + getLevel2() + "'" +
            ", tenNhomLevel2='" + getTenNhomLevel2() + "'" +
            ", level3='" + getLevel3() + "'" +
            ", tenNhomLevel3='" + getTenNhomLevel3() + "'" +
            ", level4='" + getLevel4() + "'" +
            ", tenNhomLevel4='" + getTenNhomLevel4() + "'" +
            ", maChungPnL='" + getMaChungPnL() + "'" +
            ", shortName='" + getShortName() + "'" +
            ", dVTSAP='" + getdVTSAP() + "'" +
            ", doDaiShortName=" + getDoDaiShortName() +
            ", fullName='" + getFullName() + "'" +
            ", pLSuDung='" + getpLSuDung() + "'" +
            ", pICYeuCauTaoMa='" + getpICYeuCauTaoMa() + "'" +
            ", cBLDPnLPheDuyet='" + getcBLDPnLPheDuyet() + "'" +
            ", noteKhoaMa='" + getNoteKhoaMa() + "'" +
            ", ngayTaoMa='" + getNgayTaoMa() + "'" +
            ", ghiChuKhac='" + getGhiChuKhac() + "'" +
            ", iDGuiITXuLy='" + getiDGuiITXuLy() + "'" +
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
