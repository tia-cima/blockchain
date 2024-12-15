package it.cimadomo.blockchain.service;

import it.cimadomo.blockchain.model.Transaction;

import java.util.List;

public interface MempoolService {
    void addTransaction(Transaction transaction);
    List<Transaction> getMempool();
    void removeTransactions(List<Transaction> transactions);
    void clearMempool();
}
