package com.payment.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.payment.demo.model.CardDetails;

public interface CardDetailsRepository extends  JpaRepository<CardDetails, Long> {

}
