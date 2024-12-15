package it.cimadomo.blockchain.model;

import lombok.Builder;
import lombok.Data;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.List;

@Data
@Builder
public class Block {
    private Integer index;
    private String timestamp;
    private String previousHash;
    private String hash;
    private Integer nonce;
    private Integer difficulty;
    private List<Transaction> transactions;

    @Override
    public String toString() {
        StringBuilder transactionData = new StringBuilder();
        if (transactions != null) {
            transactions.forEach(transaction -> transactionData.append(transaction.toString()));
        }
        return index + timestamp + transactionData + previousHash + nonce + difficulty;
    }

    public String retrieveHash() {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(toString().getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            return null;
        }
    }
}