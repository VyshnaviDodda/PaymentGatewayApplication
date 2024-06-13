package com.payment.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.payment.demo.model.TransactionDetails;

public interface TransactionDetailsRepository extends JpaRepository<TransactionDetails, Long> {

    List<TransactionDetails> findByMerchantDetails_MerchantId(Long merchantId);

    boolean existsByMerchantDetails_MerchantId(Long merchantId);
}