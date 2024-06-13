
package com.payment.demo.encryption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class JpaConverter implements AttributeConverter<String, String> {

    private static final String ALGORITHM = "AES";
    private static final Logger logger = LoggerFactory.getLogger(JpaConverter.class);

    SecretKey key = generateKey();

    @Override
    public String convertToDatabaseColumn(String attribute) {
        try {
            if (attribute != null) {
                Cipher cipher = Cipher.getInstance(ALGORITHM);

                cipher.init(Cipher.ENCRYPT_MODE, key);
                byte[] encryptedData = cipher.doFinal(attribute.getBytes(StandardCharsets.UTF_8));
                String encryptedString = Base64.getEncoder().encodeToString(encryptedData);
                logger.info("String encrypted successfully: {}", encryptedString);
                return encryptedString;
            }
        } catch (Exception e) {
            logger.error("Error encrypting string: {}", e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        try {
            logger.info("entered into decrypt method ");
            if (dbData != null) {
                Cipher cipher = Cipher.getInstance(ALGORITHM);
                cipher.init(Cipher.DECRYPT_MODE, key);
                byte[] decryptedData = cipher.doFinal(Base64.getDecoder().decode(dbData));
                String decryptedString = new String(decryptedData, StandardCharsets.UTF_8);
                logger.info("String decrypted successfully: {}", decryptedString);
                return decryptedString;
            }
        } catch (BadPaddingException e) {
            logger.error("Bad padding exception: {}", e.getMessage());
        } catch (Exception e) {
            logger.error("Error decrypting string: {}", e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    private SecretKey generateKey() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
            keyGenerator.init(128); // You can adjust the key size
            SecretKey secretKey = keyGenerator.generateKey();
            logger.info("AES key generated successfully");
            return secretKey;
        } catch (Exception e) {
            logger.error("Error generating AES key: {}", e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}