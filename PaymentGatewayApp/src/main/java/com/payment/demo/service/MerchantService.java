package com.payment.demo.service;

import java.security.SecureRandom;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.payment.demo.DTO.CardDetailsDTO;
import com.payment.demo.DTO.MerchantDTO;
import com.payment.demo.Enums.ClearanceStatusEnum;
import com.payment.demo.exception.MerchantNotFoundException;
import com.payment.demo.model.CardDetails;
import com.payment.demo.model.MerchantDetails;
import com.payment.demo.repo.CardDetailsRepository;
import com.payment.demo.repo.MerchantDetailsRepository;

@Service
public class MerchantService {

    private final MerchantDetailsRepository merchantDetailsRepository;
    private final CardDetailsRepository cardDetailsRepository;

    @Autowired
    public MerchantService(MerchantDetailsRepository merchantDetailsRepository,
                           CardDetailsRepository cardDetailsRepository) {
        this.merchantDetailsRepository = merchantDetailsRepository;
        this.cardDetailsRepository = cardDetailsRepository;
    }
    

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Method to create a new merchant along with associated card details
    // Method to create a new merchant along with associated card details
    public void createMerchant(MerchantDetails merchantDetails, CardDetails cardDetails) {
        // Encode the password before saving it into the database
        String encodedPassword = passwordEncoder.encode(merchantDetails.getPassword());
        merchantDetails.setPassword(encodedPassword); // Set the encoded password

        // Save the card details first to get its ID
        CardDetails savedCardDetails = cardDetailsRepository.save(cardDetails);
        // Set the saved card details in the merchant details
        merchantDetails.setCardDetails(savedCardDetails);
        // Save the merchant details with the associated card details
        merchantDetailsRepository.save(merchantDetails);
    }

    // Method to get merchant details by merchant ID
    public MerchantDetails getMerchantDetailsById(Long merchantId) {
        // Attempt to find the merchant details by ID
        Optional<MerchantDetails> merchantDetailsOptional = merchantDetailsRepository.findById(merchantId);
        
        // If present, return the merchant details; otherwise, throw an exception
        return merchantDetailsOptional.orElseThrow(() -> new MerchantNotFoundException("Merchant not found for id: " + merchantId));
    }

    // Method to generate a random merchant ID
    public Long generateRandomMerchantId() {
        // Generate a secure random 6-digit number for merchantId
        SecureRandom secureRandom = new SecureRandom();
        return (long) (100000 + secureRandom.nextInt(900000));
    }

    // Method to get merchant details with associated card details by merchant ID
    public MerchantDTO getMerchantDetailsWithCardDetails(Long merchantId) {
        // Attempt to find the merchant details by ID
        Optional<MerchantDetails> merchantDetailsOptional = merchantDetailsRepository.findById(merchantId);

        // If present, fetch the associated card details and return a simplified DTO
        if (merchantDetailsOptional.isPresent()) {
            MerchantDetails merchantDetails = merchantDetailsOptional.get();
            CardDetails cardDetails = merchantDetails.getCardDetails();

            // Create a simplified DTO with only the required fields
            MerchantDTO merchantDTO = new MerchantDTO();
            merchantDTO.setMerchantId(merchantDetails.getMerchantId());
            merchantDTO.setName(merchantDetails.getName());
            merchantDTO.setEmail(merchantDetails.getEmail());
            merchantDTO.setPhoneNumber(merchantDetails.getPhoneNumber());
            merchantDTO.setPreferredPaymentType(merchantDetails.getPreferredPaymentType());

            // Check if cardDetails is not null before accessing its fields
            if (cardDetails != null) {
                CardDetailsDTO cardDetailsDTO = new CardDetailsDTO();
                cardDetailsDTO.setCardNumber(cardDetails.getCardNumber());
                cardDetailsDTO.setName(cardDetails.getName());
                cardDetailsDTO.setExpiryDate(cardDetails.getExpiryDate());

                merchantDTO.setCardDetails(cardDetailsDTO);
            }

            return merchantDTO;
        } else {
            // If not found, throw an exception
            throw new MerchantNotFoundException("Merchant not found for id: " + merchantId);
        }
    }
    
    //Method for Login merchant
    public boolean validateMerchantCredentials(Long merchantId, String password) {
        // Fetch the merchant by the provided merchantId
        MerchantDetails merchant = merchantDetailsRepository.findById(merchantId)
                .orElse(null);
        
        // Check if the merchant exists and if the provided password matches
        return merchant != null && merchant.getPassword().equals(password);
    }
}
