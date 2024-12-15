package it.cimadomo.blockchain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Transaction {
    private String sender;
    private String recipient;
    private Double amount;
    private String data;
    private String timestamp;

    @Override
    public String toString() {
        return sender + recipient + amount + data + timestamp;
    }
}