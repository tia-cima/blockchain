package it.cimadomo.blockchain.service.impl;

import it.cimadomo.blockchain.model.Transaction;
import it.cimadomo.blockchain.service.MempoolService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MempoolServiceImpl implements MempoolService {

    private final List<Transaction> transactions = new ArrayList<>();

    @Override
    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    @Override
    public List<Transaction> getMempool() {
        return transactions;
    }

    @Override
    public void removeTransactions(List<Transaction> transactions) {
        if (transactions == null || transactions.isEmpty()) {
            return;
        }
        transactions.forEach(this.transactions::remove);
    }

    @Override
    public void clearMempool() {
        transactions.clear();
    }
}
