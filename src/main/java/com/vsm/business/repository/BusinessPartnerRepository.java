package com.vsm.business.repository;

import com.vsm.business.domain.BusinessPartner;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data SQL repository for the BusinessPartner entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BusinessPartnerRepository extends JpaRepository<BusinessPartner, Long>, JpaSpecificationExecutor<BusinessPartner> {
    List<BusinessPartner> findAllByMaChuoiAndCustomer(String maChuoi, String customer);
}
