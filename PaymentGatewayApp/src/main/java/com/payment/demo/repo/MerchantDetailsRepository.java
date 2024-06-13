package com.payment.demo.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.payment.demo.model.MerchantDetails;

public interface MerchantDetailsRepository extends JpaRepository<MerchantDetails, Long> 
{
     Optional<MerchantDetails> findByMerchantId(Long merchantId);
     
}
