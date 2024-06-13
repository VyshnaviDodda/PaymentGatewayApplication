package com.payment.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.payment.demo.DTO.CardDetailsDTO;
import com.payment.demo.DTO.MerchantDTO;
import com.payment.demo.DTO.MerchantDetailsDTO;
import com.payment.demo.DTO.OrderSummary;
import com.payment.demo.DTO.TransactionResponseDTO;
import com.payment.demo.Enums.ClearanceStatusEnum;
import com.payment.demo.Enums.TransactionStatusEnum;
import com.payment.demo.controller.CreateOrderRequest;
import com.payment.demo.exception.MerchantNotFoundException;
import com.payment.demo.exception.NotFoundException;
import com.payment.demo.exception.TransactionNotFoundException;
import com.payment.demo.model.CardDetails;
import com.payment.demo.model.MerchantDetails;
import com.payment.demo.model.TransactionDetails;
import com.payment.demo.repo.CardDetailsRepository;
import com.payment.demo.repo.CustomerRepo;
import com.payment.demo.repo.TransactionDetailsRepository;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PaymentIngestionService 
{
    private static final Logger logger = LoggerFactory.getLogger(PaymentIngestionService.class);

    private final TransactionDetailsRepository transactionDetailsRepository;
    private final PaymentProcessingService paymentProcessingService;
    private final CardDetailsRepository cardDetailsRepository;
    private final MerchantService merchantService;

    @Autowired
    public PaymentIngestionService(TransactionDetailsRepository transactionDetailsRepository,
                                   PaymentProcessingService paymentProcessingService,
                                   CustomerRepo customerDetailsRepository,
                                   CardDetailsRepository cardDetailsRepository,
                                   MerchantService merchantService) {
        this.transactionDetailsRepository = transactionDetailsRepository;
        this.paymentProcessingService = paymentProcessingService;
        this.cardDetailsRepository = cardDetailsRepository;
        this.merchantService = merchantService;
    }

    /*
     * Process the CreateOrderRequest to create a new transaction.
     * @param createOrderRequest The request containing order and payment details.
     * @return The payment status.
     */
    
    @Transactional
    public String createOrder(CreateOrderRequest createOrderRequest) {
        try {
            // Retrieve MerchantDetails by merchantId
            MerchantDetails merchantDetails = merchantService.getMerchantDetailsById(createOrderRequest.getMerchantId());

            logger.info("Received CreateOrderRequest: {}", createOrderRequest.toString());
            logger.info("Merchant ID: {}", createOrderRequest.getMerchantId());
            logger.info("Payment Request: {}", createOrderRequest.getPaymentRequest());

            // Save CardDetails to the database
            CardDetails cardDetails = createOrderRequest.getPaymentRequest().getCardDetails();
            cardDetails = cardDetailsRepository.save(cardDetails);
            
            // Extract latitude and longitude from the PaymentRequest
            String locationLatitude = createOrderRequest.getPaymentRequest().getLocationLatitude();
            String locationLongitude = createOrderRequest.getPaymentRequest().getLocationLongitude();

            // Create a new TransactionDetails
            TransactionDetails transactionDetails = new TransactionDetails();
            
            transactionDetails.setOrderId(createOrderRequest.getPaymentRequest().getOrderId());
            transactionDetails.generateTransactionId();
            transactionDetails.setAmount(createOrderRequest.getPaymentRequest().getAmount());
            transactionDetails.setCardDetails(cardDetails);
            transactionDetails.setTransactionTime();
            transactionDetails.setMerchant(merchantDetails);
            transactionDetails.setClearanceStatus(ClearanceStatusEnum.NOT_STARTED);
            transactionDetails.setTransactionStatus(TransactionStatusEnum.NOT_STARTED);
            transactionDetails.setPaymentType(createOrderRequest.getPaymentRequest().getPaymentType());
            
            // Set the latitude and longitude
            transactionDetails.setLocationLatitude(locationLatitude);
            transactionDetails.setLocationLongitude(locationLongitude);

            // Save TransactionDetails to the database
            transactionDetailsRepository.save(transactionDetails);

            // Process payment using PaymentProcessingService
            String paymentStatus = paymentProcessingService.processPayment(transactionDetails);

            logger.info("Payment processing status: {}", paymentStatus);

            return paymentStatus;
        } catch (MerchantNotFoundException e) {
            logger.error("MerchantNotFoundException: {}", e.getMessage());
            throw e; 
        } catch (Exception e) {
            logger.error("Error processing payment", e);
            throw new RuntimeException("Error processing payment", e);
        }
    }
    
    //generate random Orderid
    public static Long generateRandomOrderId() {
        SecureRandom secureRandom = new SecureRandom();
        // Generate a random 4-digit number
        return (long) (secureRandom.nextInt(9000) + 1000);
    }

    /*
     * Get the transaction ID for a given order ID.
     * @param orderId The order ID.
     * @return The transaction ID.
     */
    /*
    public String getTransactionIdForOrder(Long orderId) {
        try {
            Optional<TransactionDetails> transactionDetailsOptional = transactionDetailsRepository.findById(orderId);

            if (transactionDetailsOptional.isPresent()) {
                TransactionDetails transactionDetails = transactionDetailsOptional.get();
                return transactionDetails.getTransactionId();
            } else {
                throw new NotFoundException("Transaction not found for order id: " + orderId);
            }
        } catch (NotFoundException e) {
            logger.error("NotFoundException: {}", e.getMessage());
            throw e; // Re-throw the exception for the controller to handle
        } catch (Exception e) {
            logger.error("Error getting transaction ID for order", e);
            throw new RuntimeException("Error getting transaction ID for order", e);
        }
    }
*/
    /*
     * Get the details of an order for a given order ID.
     * @param orderId The order ID.
     * @return Map containing order details.
     */
   /* public Map<String, Object> getOrderDetails(Long orderId) {
        try {
            Optional<TransactionDetails> transactionDetailsOptional = transactionDetailsRepository.findById(orderId);

            if (transactionDetailsOptional.isPresent()) {
                TransactionDetails transactionDetails = transactionDetailsOptional.get();
                Map<String, Object> orderDetails = new HashMap<>();
                orderDetails.put("transactionId", transactionDetails.getTransactionId());
                orderDetails.put("transactionStatus", transactionDetails.getTransactionStatus());
                orderDetails.put("amount", transactionDetails.getAmount());
                orderDetails.put("cardName", transactionDetails.getCardDetails().getName());
                orderDetails.put("cardNumber", transactionDetails.getCardDetails().getCardNumber());
                orderDetails.put("transactionTime", transactionDetails.getTransactionTime());
                return orderDetails;
            } else {
                throw new NotFoundException("Transaction not found for order id: " + orderId);
            }
        } catch (NotFoundException e) {
            logger.error("NotFoundException: {}", e.getMessage());
            throw e; // Re-throw the exception for the controller to handle
        } catch (Exception e) {
            logger.error("Error getting order details", e);
            throw new RuntimeException("Error getting order details", e);
        }
    }
    
    */

    /*
     * Get a list of orders for a given merchant ID.
     * @param merchantId The merchant ID.
     * @return List of TransactionDetails for the merchant.
     */
    public List<TransactionResponseDTO> getOrdersByMerchantId(Long merchantId) {
        try {
            // Check if the merchant exists in TransactionDetails table
            boolean merchantExists = transactionDetailsRepository.existsByMerchantDetails_MerchantId(merchantId);

            if (!merchantExists) {
                throw new MerchantNotFoundException("Merchant not found for ID: " + merchantId);
            }

            List<TransactionDetails> orders = transactionDetailsRepository.findByMerchantDetails_MerchantId(merchantId);
            return convertToResponseDTOList(orders);
            
        } catch (MerchantNotFoundException e) {
            logger.error("MerchantNotFoundException: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error fetching orders by merchant ID", e);
            throw new RuntimeException("Error fetching orders by merchant ID", e);
        }
    }

    private List<TransactionResponseDTO> convertToResponseDTOList(List<TransactionDetails> orders) {
        List<TransactionResponseDTO> responseDTOList = new ArrayList<>();
        for (TransactionDetails order : orders) {
            TransactionResponseDTO responseDTO = convertToResponseDTO(order);
            responseDTOList.add(responseDTO);
        }
        return responseDTOList;
    }

    private TransactionResponseDTO convertToResponseDTO(TransactionDetails order) {
        TransactionResponseDTO responseDTO = new TransactionResponseDTO();
        responseDTO.setOrderId(order.getOrderId());
        responseDTO.setAmount(order.getAmount());
        responseDTO.setPaymentType(order.getPaymentType());
        responseDTO.setTransactionStatus(order.getTransactionStatus());
        responseDTO.setClearanceStatus(order.getClearanceStatus());
        responseDTO.setLocationLatitude(order.getLocationLatitude());
        responseDTO.setLocationLongitude(order.getLocationLongitude());
        responseDTO.setTransactionTime(order.getTransactionTime());

        // Set CardDetailsDTO
        CardDetailsDTO cardDetailsDTO = new CardDetailsDTO();
        cardDetailsDTO.setCardNumber(order.getCardDetails().getCardNumber());
        cardDetailsDTO.setName(order.getCardDetails().getName());
        cardDetailsDTO.setExpiryDate(order.getCardDetails().getExpiryDate());
        responseDTO.setCardDetailsDTO(cardDetailsDTO);

        // Set MerchantDetailsDTO
        MerchantDetailsDTO merchantDetailsDTO = new MerchantDetailsDTO();
        merchantDetailsDTO.setName(order.getMerchantDetails().getName());
        merchantDetailsDTO.setEmail(order.getMerchantDetails().getEmail());
        merchantDetailsDTO.setPhoneNumber(order.getMerchantDetails().getPhoneNumber());
        responseDTO.setMerchantDetailsDTO(merchantDetailsDTO);

        return responseDTO;
    }
    /*
     * Get merchant details list for merchant ID.
     * @param merchantId The merchant ID.
     */
/*  //this is for fetch the required details from dto
	   
    public List<OrderSummary> getOrdersDTOByMerchantId(Long merchantId) {
        try {
            // Fetch transactions based on the merchant ID
            logger.info("Fetching orders for merchant ID: {}", merchantId);

            List<TransactionDetails> orders = transactionDetailsRepository.findByMerchantDetails_MerchantId(merchantId);

            List<OrderSummary> orderSummaries = orders.stream().map(order -> {
                OrderSummary orderSummary = new OrderSummary();
                orderSummary.setClearanceStatus(order.getClearanceStatus());

                // Create a modified MerchantDetailsDTO without CVV
                MerchantDTO merchantDTO = new MerchantDTO();
                merchantDTO.setName(order.getMerchantDetails().getName());
                merchantDTO.setEmail(order.getMerchantDetails().getEmail());
                merchantDTO.setPhoneNumber(order.getMerchantDetails().getPhoneNumber());
                merchantDTO.setPreferredPaymentType(order.getMerchantDetails().getPreferredPaymentType());

                // Create a modified CardDetailsDTO without CVV
                CardDetailsDTO cardDetailsDTO = new CardDetailsDTO();
                cardDetailsDTO.setCardNumber(merchantDetailsRepo.getCardDetails().getCardNumber()); // Fix here
                cardDetailsDTO.setName(merchantDTO.getCardDetails().getName()); // Fix here
                cardDetailsDTO.setExpiryDate(merchantDTO.getCardDetails().getExpiryDate()); // Fix here

                merchantDTO.setCardDetails(cardDetailsDTO);

                orderSummary.setMerchantDetailsDTO(merchantDTO);
                return orderSummary;
            }).collect(Collectors.toList());

            logger.info("Fetched orders by merchant successfully");
            return orderSummaries;

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error fetching orders for merchant ID: {}", merchantId, e);
            throw new RuntimeException("Error fetching orders for merchant ID: " + merchantId);
        }
    }
*/
}
    

