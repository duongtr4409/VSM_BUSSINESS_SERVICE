package com.vsm.business.repository;

import com.vsm.business.domain.MallAndStall;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data SQL repository for the MallAndStall entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MallAndStallRepository extends JpaRepository<MallAndStall, Long>, JpaSpecificationExecutor<MallAndStall> {
    List<MallAndStall> findAllByCompanyCodeAndMaKhachHang(String companyCode, String maKhachHang);
}
