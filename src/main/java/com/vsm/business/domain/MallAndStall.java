package com.vsm.business.domain;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;

/**
 * A MallAndStall.
 */
@Entity
@Table(name = "mall_and_stall")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "mallandstall")
public class MallAndStall implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "stt")
    private Long stt;

    @Column(name = "company_code")
    private String companyCode;

    @Column(name = "profit_center")
    private String profitCenter;

    @Column(name = "profit_center_name")
    private String profitCenterName;

    @Column(name = "business_entity")
    private String businessEntity;

    @Column(name = "ma_khach_hang")
    private String maKhachHang;

    @Column(name = "ten_khach_hang")
    private String tenKhachHang;

    @Column(name = "ten_nvkd")
    private String tenNVKD;

    @Column(name = "so_lo")
    private String soLo;

    @Column(name = "ten_gian_hang")
    private String tenGianHang;

    @Column(name = "dien_tich")
    private Double dienTich;

    @Column(name = "ma_hop_dong")
    private String maHopDong;

    @Column(name = "ngay_ket_thuc_hd")
    private Instant ngayKetThucHD;

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

    public MallAndStall id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStt() {
        return this.stt;
    }

    public MallAndStall stt(Long stt) {
        this.setStt(stt);
        return this;
    }

    public void setStt(Long stt) {
        this.stt = stt;
    }

    public String getCompanyCode() {
        return this.companyCode;
    }

    public MallAndStall companyCode(String companyCode) {
        this.setCompanyCode(companyCode);
        return this;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getProfitCenter() {
        return this.profitCenter;
    }

    public MallAndStall profitCenter(String profitCenter) {
        this.setProfitCenter(profitCenter);
        return this;
    }

    public void setProfitCenter(String profitCenter) {
        this.profitCenter = profitCenter;
    }

    public String getProfitCenterName() {
        return this.profitCenterName;
    }

    public MallAndStall profitCenterName(String profitCenterName) {
        this.setProfitCenterName(profitCenterName);
        return this;
    }

    public void setProfitCenterName(String profitCenterName) {
        this.profitCenterName = profitCenterName;
    }

    public String getBusinessEntity() {
        return this.businessEntity;
    }

    public MallAndStall businessEntity(String businessEntity) {
        this.setBusinessEntity(businessEntity);
        return this;
    }

    public void setBusinessEntity(String businessEntity) {
        this.businessEntity = businessEntity;
    }

    public String getMaKhachHang() {
        return this.maKhachHang;
    }

    public MallAndStall maKhachHang(String maKhachHang) {
        this.setMaKhachHang(maKhachHang);
        return this;
    }

    public void setMaKhachHang(String maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public String getTenKhachHang() {
        return this.tenKhachHang;
    }

    public MallAndStall tenKhachHang(String tenKhachHang) {
        this.setTenKhachHang(tenKhachHang);
        return this;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }

    public String getTenNVKD() {
        return this.tenNVKD;
    }

    public MallAndStall tenNVKD(String tenNVKD) {
        this.setTenNVKD(tenNVKD);
        return this;
    }

    public void setTenNVKD(String tenNVKD) {
        this.tenNVKD = tenNVKD;
    }

    public String getSoLo() {
        return this.soLo;
    }

    public MallAndStall soLo(String soLo) {
        this.setSoLo(soLo);
        return this;
    }

    public void setSoLo(String soLo) {
        this.soLo = soLo;
    }

    public String getTenGianHang() {
        return this.tenGianHang;
    }

    public MallAndStall tenGianHang(String tenGianHang) {
        this.setTenGianHang(tenGianHang);
        return this;
    }

    public void setTenGianHang(String tenGianHang) {
        this.tenGianHang = tenGianHang;
    }

    public Double getDienTich() {
        return this.dienTich;
    }

    public MallAndStall dienTich(Double dienTich) {
        this.setDienTich(dienTich);
        return this;
    }

    public void setDienTich(Double dienTich) {
        this.dienTich = dienTich;
    }

    public String getMaHopDong() {
        return this.maHopDong;
    }

    public MallAndStall maHopDong(String maHopDong) {
        this.setMaHopDong(maHopDong);
        return this;
    }

    public void setMaHopDong(String maHopDong) {
        this.maHopDong = maHopDong;
    }

    public Instant getNgayKetThucHD() {
        return this.ngayKetThucHD;
    }

    public MallAndStall ngayKetThucHD(Instant ngayKetThucHD) {
        this.setNgayKetThucHD(ngayKetThucHD);
        return this;
    }

    public void setNgayKetThucHD(Instant ngayKetThucHD) {
        this.ngayKetThucHD = ngayKetThucHD;
    }

    public Boolean getIsSyncFromSAP() {
        return this.isSyncFromSAP;
    }

    public MallAndStall isSyncFromSAP(Boolean isSyncFromSAP) {
        this.setIsSyncFromSAP(isSyncFromSAP);
        return this;
    }

    public void setIsSyncFromSAP(Boolean isSyncFromSAP) {
        this.isSyncFromSAP = isSyncFromSAP;
    }

    public Instant getTimeSync() {
        return this.timeSync;
    }

    public MallAndStall timeSync(Instant timeSync) {
        this.setTimeSync(timeSync);
        return this;
    }

    public void setTimeSync(Instant timeSync) {
        this.timeSync = timeSync;
    }

    public String getDescription() {
        return this.description;
    }

    public MallAndStall description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedName() {
        return this.createdName;
    }

    public MallAndStall createdName(String createdName) {
        this.setCreatedName(createdName);
        return this;
    }

    public void setCreatedName(String createdName) {
        this.createdName = createdName;
    }

    public String getCreatedOrgName() {
        return this.createdOrgName;
    }

    public MallAndStall createdOrgName(String createdOrgName) {
        this.setCreatedOrgName(createdOrgName);
        return this;
    }

    public void setCreatedOrgName(String createdOrgName) {
        this.createdOrgName = createdOrgName;
    }

    public String getCreatedRankName() {
        return this.createdRankName;
    }

    public MallAndStall createdRankName(String createdRankName) {
        this.setCreatedRankName(createdRankName);
        return this;
    }

    public void setCreatedRankName(String createdRankName) {
        this.createdRankName = createdRankName;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public MallAndStall createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedName() {
        return this.modifiedName;
    }

    public MallAndStall modifiedName(String modifiedName) {
        this.setModifiedName(modifiedName);
        return this;
    }

    public void setModifiedName(String modifiedName) {
        this.modifiedName = modifiedName;
    }

    public Instant getModifiedDate() {
        return this.modifiedDate;
    }

    public MallAndStall modifiedDate(Instant modifiedDate) {
        this.setModifiedDate(modifiedDate);
        return this;
    }

    public void setModifiedDate(Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public MallAndStall isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getIsDelete() {
        return this.isDelete;
    }

    public MallAndStall isDelete(Boolean isDelete) {
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
        if (!(o instanceof MallAndStall)) {
            return false;
        }
        return id != null && id.equals(((MallAndStall) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MallAndStall{" +
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
