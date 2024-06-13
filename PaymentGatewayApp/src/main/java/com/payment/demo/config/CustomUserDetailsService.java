package com.payment.demo.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.payment.demo.model.MerchantDetails;
import com.payment.demo.repo.MerchantDetailsRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final MerchantDetailsRepository merchantDetailsRepository;

    public CustomUserDetailsService(MerchantDetailsRepository merchantDetailsRepository) {
        this.merchantDetailsRepository = merchantDetailsRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String merchantId) throws UsernameNotFoundException {
        MerchantDetails merchantDetails = merchantDetailsRepository.findByMerchantId(Long.parseLong(merchantId))
                .orElseThrow(() -> new UsernameNotFoundException("Merchant not found with ID: " + merchantId));

        return org.springframework.security.core.userdetails.User.withUsername(merchantId)
                .password(merchantDetails.getPassword())
                .roles("MERCHANT")
                .build();
    }
}

