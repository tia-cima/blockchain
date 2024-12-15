package it.cimadomo.blockchain.controller;

import it.cimadomo.blockchain.model.Block;
import it.cimadomo.blockchain.service.BlockchainService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/blockchain")
@AllArgsConstructor
public class BlockchainController {

    private final Integer REWARD = 100;
    private final BlockchainService blockchainService;

    @GetMapping("/get")
    public ResponseEntity<Object> getBlockchain() {
        return ResponseEntity.ok(blockchainService.getBlockChain());
    }

    @GetMapping("/get/latest")
    public ResponseEntity<Block> getLatestBlock() {
        return ResponseEntity.ok(blockchainService.getLatestBlock());
    }

    @PostMapping("/add")
    public ResponseEntity<Integer> addBlock(@RequestBody Block block) {
        boolean success = blockchainService.addBlock(block);
        return success
                ? ResponseEntity.ok(REWARD)
                : ResponseEntity.status(403).build();
    }
}