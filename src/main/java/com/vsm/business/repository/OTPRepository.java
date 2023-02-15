package com.vsm.business.repository;

import com.vsm.business.domain.OTP;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data SQL repository for the OTP entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OTPRepository extends JpaRepository<OTP, Long>, JpaSpecificationExecutor<OTP> {
    List<OTP> findAll();
    List<OTP> findAllByRequestDataId(Long requestDataId);
    @Query(value = "select * From otp where request_data_id = :requestDataId and o_tp_code = :otpCode", nativeQuery = true)
    List<OTP> customGetAllByRequestDataIdAndOTPCode(@Param("requestDataId") Long requestDataId, @Param("otpCode") String otpCode);
}
