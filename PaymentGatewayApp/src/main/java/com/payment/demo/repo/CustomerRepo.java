package com.payment.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.payment.demo.model.CustomerDetails;

public interface CustomerRepo extends JpaRepository<CustomerDetails, Long>
{

}