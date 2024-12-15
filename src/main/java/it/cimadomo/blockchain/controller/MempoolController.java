package it.cimadomo.blockchain.controller;

import it.cimadomo.blockchain.model.Transaction;
import it.cimadomo.blockchain.service.MempoolService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
@AllArgsConstructor
public class MempoolController {

    private final MempoolService mempoolService;

    @GetMapping("/get")
    public ResponseEntity<List<Transaction>> getMempool() {
        return ResponseEntity.ok(mempoolService.getMempool());
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addTransaction(@RequestBody Transaction transaction) {
        mempoolService.addTransaction(transaction);
        return ResponseEntity.ok().build();
    }
}
