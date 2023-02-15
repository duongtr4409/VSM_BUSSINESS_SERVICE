package com.vsm.business.domain;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;

/**
 * A CentralizedShopping.
 */
@Entity
@Table(name = "centralized_shopping")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "centralizedshopping")
public class CentralizedShopping implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "stt")
    private Long stt;

    @Column(name = "bang_so")
    private Long bangSo;

    @Column(name = "level_1")
    private String level1;

    @Column(name = "ten_nhom_level_1")
    private String tenNhomLevel1;

    @Column(name = "level_2")
    private String level2;

    @Column(name = "ten_nhom_level_2")
    private String tenNhomLevel2;

    @Column(name = "level_3")
    private String level3;

    @Column(name = "ten_nhom_level_3")
    private String tenNhomLevel3;

    @Column(name = "level_4")
    private String level4;

    @Column(name = "ten_nhom_level_4")
    private String tenNhomLevel4;

    @Column(name = "ma_chung_pn_l")
    private String maChungPnL;

    @Column(name = "short_name")
    private String shortName;

    @Column(name = "d_vtsap")
    private String dVTSAP;

    @Column(name = "do_dai_short_name")
    private Long doDaiShortName;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "p_l_su_dung")
    private String pLSuDung;

    @Column(name = "p_ic_yeu_cau_tao_ma")
    private String pICYeuCauTaoMa;

    @Column(name = "c_bld_pn_l_phe_duyet")
    private String cBLDPnLPheDuyet;

    @Column(name = "note_khoa_ma")
    private String noteKhoaMa;

    @Column(name = "ngay_tao_ma")
    private Instant ngayTaoMa;

    @Column(name = "ghi_chu_khac")
    private String ghiChuKhac;

    @Column(name = "i_d_gui_it_xu_ly")
    private String iDGuiITXuLy;

    @Column(name = "is_sync_from_sap")
    private Boolean isSyncFromSAP;

    @Column(name = "time_sync")
    private Instant timeSync;

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

    public CentralizedShopping id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStt() {
        return this.stt;
    }

    public CentralizedShopping stt(Long stt) {
        this.setStt(stt);
        return this;
    }

    public void setStt(Long stt) {
        this.stt = stt;
    }

    public Long getBangSo() {
        return this.bangSo;
    }

    public CentralizedShopping bangSo(Long bangSo) {
        this.setBangSo(bangSo);
        return this;
    }

    public void setBangSo(Long bangSo) {
        this.bangSo = bangSo;
    }

    public String getLevel1() {
        return this.level1;
    }

    public CentralizedShopping level1(String level1) {
        this.setLevel1(level1);
        return this;
    }

    public void setLevel1(String level1) {
        this.level1 = level1;
    }

    public String getTenNhomLevel1() {
        return this.tenNhomLevel1;
    }

    public CentralizedShopping tenNhomLevel1(String tenNhomLevel1) {
        this.setTenNhomLevel1(tenNhomLevel1);
        return this;
    }

    public void setTenNhomLevel1(String tenNhomLevel1) {
        this.tenNhomLevel1 = tenNhomLevel1;
    }

    public String getLevel2() {
        return this.level2;
    }

    public CentralizedShopping level2(String level2) {
        this.setLevel2(level2);
        return this;
    }

    public void setLevel2(String level2) {
        this.level2 = level2;
    }

    public String getTenNhomLevel2() {
        return this.tenNhomLevel2;
    }

    public CentralizedShopping tenNhomLevel2(String tenNhomLevel2) {
        this.setTenNhomLevel2(tenNhomLevel2);
        return this;
    }

    public void setTenNhomLevel2(String tenNhomLevel2) {
        this.tenNhomLevel2 = tenNhomLevel2;
    }

    public String getLevel3() {
        return this.level3;
    }

    public CentralizedShopping level3(String level3) {
        this.setLevel3(level3);
        return this;
    }

    public void setLevel3(String level3) {
        this.level3 = level3;
    }

    public String getTenNhomLevel3() {
        return this.tenNhomLevel3;
    }

    public CentralizedShopping tenNhomLevel3(String tenNhomLevel3) {
        this.setTenNhomLevel3(tenNhomLevel3);
        return this;
    }

    public void setTenNhomLevel3(String tenNhomLevel3) {
        this.tenNhomLevel3 = tenNhomLevel3;
    }

    public String getLevel4() {
        return this.level4;
    }

    public CentralizedShopping level4(String level4) {
        this.setLevel4(level4);
        return this;
    }

    public void setLevel4(String level4) {
        this.level4 = level4;
    }

    public String getTenNhomLevel4() {
        return this.tenNhomLevel4;
    }

    public CentralizedShopping tenNhomLevel4(String tenNhomLevel4) {
        this.setTenNhomLevel4(tenNhomLevel4);
        return this;
    }

    public void setTenNhomLevel4(String tenNhomLevel4) {
        this.tenNhomLevel4 = tenNhomLevel4;
    }

    public String getMaChungPnL() {
        return this.maChungPnL;
    }

    public CentralizedShopping maChungPnL(String maChungPnL) {
        this.setMaChungPnL(maChungPnL);
        return this;
    }

    public void setMaChungPnL(String maChungPnL) {
        this.maChungPnL = maChungPnL;
    }

    public String getShortName() {
        return this.shortName;
    }

    public CentralizedShopping shortName(String shortName) {
        this.setShortName(shortName);
        return this;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getdVTSAP() {
        return this.dVTSAP;
    }

    public CentralizedShopping dVTSAP(String dVTSAP) {
        this.setdVTSAP(dVTSAP);
        return this;
    }

    public void setdVTSAP(String dVTSAP) {
        this.dVTSAP = dVTSAP;
    }

    public Long getDoDaiShortName() {
        return this.doDaiShortName;
    }

    public CentralizedShopping doDaiShortName(Long doDaiShortName) {
        this.setDoDaiShortName(doDaiShortName);
        return this;
    }

    public void setDoDaiShortName(Long doDaiShortName) {
        this.doDaiShortName = doDaiShortName;
    }

    public String getFullName() {
        return this.fullName;
    }

    public CentralizedShopping fullName(String fullName) {
        this.setFullName(fullName);
        return this;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getpLSuDung() {
        return this.pLSuDung;
    }

    public CentralizedShopping pLSuDung(String pLSuDung) {
        this.setpLSuDung(pLSuDung);
        return this;
    }

    public void setpLSuDung(String pLSuDung) {
        this.pLSuDung = pLSuDung;
    }

    public String getpICYeuCauTaoMa() {
        return this.pICYeuCauTaoMa;
    }

    public CentralizedShopping pICYeuCauTaoMa(String pICYeuCauTaoMa) {
        this.setpICYeuCauTaoMa(pICYeuCauTaoMa);
        return this;
    }

    public void setpICYeuCauTaoMa(String pICYeuCauTaoMa) {
        this.pICYeuCauTaoMa = pICYeuCauTaoMa;
    }

    public String getcBLDPnLPheDuyet() {
        return this.cBLDPnLPheDuyet;
    }

    public CentralizedShopping cBLDPnLPheDuyet(String cBLDPnLPheDuyet) {
        this.setcBLDPnLPheDuyet(cBLDPnLPheDuyet);
        return this;
    }

    public void setcBLDPnLPheDuyet(String cBLDPnLPheDuyet) {
        this.cBLDPnLPheDuyet = cBLDPnLPheDuyet;
    }

    public String getNoteKhoaMa() {
        return this.noteKhoaMa;
    }

    public CentralizedShopping noteKhoaMa(String noteKhoaMa) {
        this.setNoteKhoaMa(noteKhoaMa);
        return this;
    }

    public void setNoteKhoaMa(String noteKhoaMa) {
        this.noteKhoaMa = noteKhoaMa;
    }

    public Instant getNgayTaoMa() {
        return this.ngayTaoMa;
    }

    public CentralizedShopping ngayTaoMa(Instant ngayTaoMa) {
        this.setNgayTaoMa(ngayTaoMa);
        return this;
    }

    public void setNgayTaoMa(Instant ngayTaoMa) {
        this.ngayTaoMa = ngayTaoMa;
    }

    public String getGhiChuKhac() {
        return this.ghiChuKhac;
    }

    public CentralizedShopping ghiChuKhac(String ghiChuKhac) {
        this.setGhiChuKhac(ghiChuKhac);
        return this;
    }

    public void setGhiChuKhac(String ghiChuKhac) {
        this.ghiChuKhac = ghiChuKhac;
    }

    public String getiDGuiITXuLy() {
        return this.iDGuiITXuLy;
    }

    public CentralizedShopping iDGuiITXuLy(String iDGuiITXuLy) {
        this.setiDGuiITXuLy(iDGuiITXuLy);
        return this;
    }

    public void setiDGuiITXuLy(String iDGuiITXuLy) {
        this.iDGuiITXuLy = iDGuiITXuLy;
    }

    public Boolean getIsSyncFromSAP() {
        return this.isSyncFromSAP;
    }

    public CentralizedShopping isSyncFromSAP(Boolean isSyncFromSAP) {
        this.setIsSyncFromSAP(isSyncFromSAP);
        return this;
    }

    public void setIsSyncFromSAP(Boolean isSyncFromSAP) {
        this.isSyncFromSAP = isSyncFromSAP;
    }

    public Instant getTimeSync() {
        return this.timeSync;
    }

    public CentralizedShopping timeSync(Instant timeSync) {
        this.setTimeSync(timeSync);
        return this;
    }

    public void setTimeSync(Instant timeSync) {
        this.timeSync = timeSync;
    }

    public String getDescription() {
        return this.description;
    }

    public CentralizedShopping description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedName() {
        return this.createdName;
    }

    public CentralizedShopping createdName(String createdName) {
        this.setCreatedName(createdName);
        return this;
    }

    public void setCreatedName(String createdName) {
        this.createdName = createdName;
    }

    public String getCreatedOrgName() {
        return this.createdOrgName;
    }

    public CentralizedShopping createdOrgName(String createdOrgName) {
        this.setCreatedOrgName(createdOrgName);
        return this;
    }

    public void setCreatedOrgName(String createdOrgName) {
        this.createdOrgName = createdOrgName;
    }

    public String getCreatedRankName() {
        return this.createdRankName;
    }

    public CentralizedShopping createdRankName(String createdRankName) {
        this.setCreatedRankName(createdRankName);
        return this;
    }

    public void setCreatedRankName(String createdRankName) {
        this.createdRankName = createdRankName;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public CentralizedShopping createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedName() {
        return this.modifiedName;
    }

    public CentralizedShopping modifiedName(String modifiedName) {
        this.setModifiedName(modifiedName);
        return this;
    }

    public void setModifiedName(String modifiedName) {
        this.modifiedName = modifiedName;
    }

    public Instant getModifiedDate() {
        return this.modifiedDate;
    }

    public CentralizedShopping modifiedDate(Instant modifiedDate) {
        this.setModifiedDate(modifiedDate);
        return this;
    }

    public void setModifiedDate(Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public CentralizedShopping isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getIsDelete() {
        return this.isDelete;
    }

    public CentralizedShopping isDelete(Boolean isDelete) {
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
        if (!(o instanceof CentralizedShopping)) {
            return false;
        }
        return id != null && id.equals(((CentralizedShopping) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CentralizedShopping{" +
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
