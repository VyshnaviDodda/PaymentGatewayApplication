package com.payment.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import com.payment.demo.DTO.MerchantDTO;
import com.payment.demo.DTO.OrderSummary;
import com.payment.demo.DTO.TransactionResponseDTO;
import com.payment.demo.exception.MerchantNotFoundException;
import com.payment.demo.exception.OrderFetchException;
import com.payment.demo.model.CardDetails;
import com.payment.demo.model.MerchantDetails;
import com.payment.demo.service.MerchantService;
import com.payment.demo.service.PaymentIngestionService;

@RestController
@RequestMapping("/gateway")
public class PaymentGatewayController {

    private static final Logger logger = LoggerFactory.getLogger(PaymentGatewayController.class);

    private final PaymentIngestionService paymentIngestionService;
    private MerchantService merchantService;

    @Autowired
    public PaymentGatewayController(PaymentIngestionService paymentIngestionService, MerchantService merchantService) {
        this.paymentIngestionService = paymentIngestionService;
        this.merchantService = merchantService;
    }

    // CreateOrder API
    @PostMapping("/createOrder")
    public String createOrder(@RequestBody CreateOrderRequest createOrderRequest) {
        logger.info("Received CreateOrderRequest: {}", createOrderRequest.toString());
        System.out.println("VYSHNAVI");
        
        Long orderId = PaymentIngestionService.generateRandomOrderId();
        createOrderRequest.getPaymentRequest().setOrderId(orderId);

        logger.info("Created Order with OrderId: {}", orderId);

        return paymentIngestionService.createOrder(createOrderRequest);
    }

    // Getting TransactionId by using OrderId
 /*   @GetMapping("/getTransactionIdForOrder")
    public ResponseEntity<String> getTransactionIdForOrder(@RequestParam(name = "orderId") Long orderId) {
        try {
            logger.info("Received request for orderId: {}", orderId);
            String transactionId = paymentIngestionService.getTransactionIdForOrder(orderId);
            return ResponseEntity.ok(transactionId);
        } catch (NotFoundException e) {
            logger.error("NotFoundException: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Error retrieving transaction id", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving transaction id");
        }
    }
*/
    
    // Getting Transaction Details by using OrderId
/*    @GetMapping("/getOrderDetails")
    public ResponseEntity<Map<String, Object>> getOrderDetails(@RequestParam(name = "orderId") Long orderId) {
        try {
            logger.info("Received request for orderId: {}", orderId);
            Map<String, Object> orderDetails = paymentIngestionService.getOrderDetails(orderId);
            return ResponseEntity.ok(orderDetails);
        } catch (NotFoundException e) {
            logger.error("NotFoundException: {}", e.getMessage());
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (Exception e) {
            logger.error("Error retrieving order details", e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Error retrieving order details");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
*/
    // Getting Transaction Details using MerchantId
    @GetMapping("/getOrdersByMerchant")
    public ResponseEntity<List<TransactionResponseDTO>> getOrdersByMerchant(@RequestParam(name = "merchantId") Long merchantId) {
        try {
            logger.info("Received request for merchantId: {}", merchantId);

            List<TransactionResponseDTO> orders = paymentIngestionService.getOrdersByMerchantId(merchantId);

            logger.info("Returning {} orders for merchantId: {}", orders.size(), merchantId);

            return ResponseEntity.ok(orders);
        } catch (MerchantNotFoundException e) {
            logger.warn("Merchant not found for ID: {}", merchantId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            logger.error("Error retrieving orders by merchant", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // CreateMerchant API
    @PostMapping("/createMerchant")
    public ResponseEntity<String> createMerchant(@RequestBody CreateMerchantRequest createMerchantRequest) {
        try {
            MerchantDetails merchantDetails = createMerchantRequest.getMerchantDetails();
            CardDetails cardDetails = createMerchantRequest.getCardDetails();

            // Generate a random merchantId
            Long merchantId = merchantService.generateRandomMerchantId();

            // Set the generated merchantId
            merchantDetails.setMerchantId(merchantId);

            merchantService.createMerchant(merchantDetails, cardDetails);
            String successMessage = "Merchant created successfully with merchant Id: " + merchantId;
            logger.info(successMessage);

            return ResponseEntity.status(HttpStatus.CREATED).body(successMessage);
        } catch (Exception e) {
            logger.error("Failed to create merchant", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create merchant");
        }
    }
    
    //Merchant Login
    @PostMapping("/Merchantlogin")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        try {
            Long merchantId = loginRequest.getMerchantId();
            String password = loginRequest.getPassword();
            
            // Validate merchant credentials
            boolean isValid = merchantService.validateMerchantCredentials(merchantId, password);
            
            if (isValid) {
                logger.info("Login successful for merchant ID: {}", merchantId);
                return ResponseEntity.ok("Login successful");
            } else {
                logger.warn("Invalid credentials for merchant ID: {}", merchantId);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid MerchantId or Password");
            }
        } catch (Exception e) {
            logger.error("Failed to process login request", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to process login request");
        }
    }

    

    // Get Merchant Details After Register
    @GetMapping("/getmerchantDetailsByMerchantId")
    public ResponseEntity<MerchantDTO> getMerchantDetails(@RequestParam(name = "merchantId") Long merchantId) {
        try {
            MerchantDTO merchantDetailsDTO = merchantService.getMerchantDetailsWithCardDetails(merchantId);
            return new ResponseEntity<>(merchantDetailsDTO, HttpStatus.OK);
        } catch (MerchantNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    
    @GetMapping("/getMerchantPassword")
    public ResponseEntity<String> getMerchantPassword(@RequestParam(name = "merchantId") Long merchantId) {
        try {
            // Fetch the merchant details by ID
            MerchantDetails merchantDetails = merchantService.getMerchantDetailsById(merchantId);
            
            // Retrieve the password (this is for demonstration purposes only, NOT recommended in real-world applications)
            String password = merchantDetails.getPassword();
            
            // Return the password (again, this is for demonstration purposes only)
            return ResponseEntity.ok(password);
        } catch (MerchantNotFoundException e) {
            // Handle the case where the merchant is not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Merchant not found for id: " + merchantId);
        } catch (Exception e) {
            // Handle other exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve password");
        }
    }
}
