//package com.payment.demo.encryption;
//
//import java.nio.charset.StandardCharsets;
//import java.util.Base64;
//import javax.crypto.Cipher;
//import javax.crypto.KeyGenerator;
//import javax.crypto.SecretKey;
//
//import jakarta.persistence.AttributeConverter;
//import jakarta.persistence.Converter;
//
//@Converter
//public class CardNumJpaConverter implements AttributeConverter<String, String> {
//
//    private static final String ALGORITHM = "AES";
//    private static final int VISIBLE_DIGITS = 4;
//
//    private SecretKey key = generateKey();
//
//    @Override
//    public String convertToDatabaseColumn(String attribute) {
//        try {
//            if (attribute != null && attribute.length() == 16) {
//                Cipher cipher = Cipher.getInstance(ALGORITHM);
//                cipher.init(Cipher.ENCRYPT_MODE, key);
//
//                // Encrypt only the first 12 digits
//                String first12Digits = attribute.substring(0, 12);
//                byte[] encryptedData = cipher.doFinal(first12Digits.getBytes(StandardCharsets.UTF_8));
//
//                // Combine encrypted part and last 4 visible digits
//                String encryptedPart = Base64.getEncoder().encodeToString(encryptedData);
//                return encryptedPart + attribute.substring(12);
//            }
//        } catch (Exception e) {
//            // Handle the exception
//            e.printStackTrace();
//        }
//        return null;
//    }
//    @Override
//    public String convertToEntityAttribute(String dbData) {
//        try {
//            if (dbData != null && dbData.length() > VISIBLE_DIGITS) {
//                Cipher cipher = Cipher.getInstance(ALGORITHM);
//                cipher.init(Cipher.DECRYPT_MODE, key);
//
//                // Determine the length of the encrypted part
//                int encryptedPartLength = dbData.length() - VISIBLE_DIGITS;
//
//                // Decrypt the encrypted part
//                byte[] decryptedData = cipher.doFinal(Base64.getDecoder().decode(dbData.substring(0, encryptedPartLength)));
//                String decryptedPart = new String(decryptedData, StandardCharsets.UTF_8);
//
//                // Combine decrypted part and last 4 visible digits
//                String result = decryptedPart + dbData.substring(encryptedPartLength);
//                System.out.println("Decrypted CardNumber: " + result);
//                return result;
//            }
//        } catch (Exception e) {
//            // Handle the exception
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//
//    private SecretKey generateKey() {
//        try {
//            KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
//            keyGenerator.init(128); // You can adjust the key size
//            return keyGenerator.generateKey();
//        } catch (Exception e) {
//            // Handle the exception
//            e.printStackTrace();
//        }
//        return null;
//    }
//}
