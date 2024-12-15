package it.cimadomo.blockchain.service.impl;

import it.cimadomo.blockchain.model.Block;
import it.cimadomo.blockchain.service.BlockchainService;
import it.cimadomo.blockchain.service.MempoolService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Service
@Slf4j
public class BlockchainServiceImpl implements BlockchainService {

    private final List<Block> blockChain = new ArrayList<>();
    private final Integer DIFFICULTY = 6;
    private final MempoolService mempoolService;

    public BlockchainServiceImpl(MempoolService mempoolService) {
        this.mempoolService = mempoolService;
        Block genesisBlock = Block.builder()
                .index(0)
                .timestamp(Instant.now().toString())
                .previousHash("0")
                .nonce(0)
                .difficulty(DIFFICULTY)
                .hash("0")
                .transactions(new ArrayList<>())
                .build();
        genesisBlock.setHash(genesisBlock.retrieveHash());
        blockChain.add(genesisBlock);
        log.info("Genesis block added to the blockchain: {}", genesisBlock);
    }

    @Override
    public Block getLatestBlock(){
        return blockChain.getLast();
    }

    @Override
    public boolean isBlockValid(Block block, Block previousBlock){
        if (!block.getPreviousHash().equals(previousBlock.getHash())) {
            log.error("Current block's previous hash is not equal to the previous block's hash");
            return false;
        }

        if (!block.getHash().equals(block.retrieveHash())) {
            log.error("Current block hash is not equal to the calculated hash, might have been violated");
            return false;
        }
        String prefix = "0".repeat(block.getDifficulty());
        if(!block.getHash().startsWith(prefix)){
            log.error("Block hash does not meet the difficulty requirement");
            return false;
        }
        return true;
    }

    @Override
    public boolean addBlock(Block block){
        block.setDifficulty(DIFFICULTY);
        Block latestBlock = getLatestBlock();
        if(!isBlockValid(block, latestBlock)){
            log.error("Block is not valid: {}", block);
            return false;
        }
        blockChain.add(block);
        log.info("New block added to the blockchain: {}", block);
        if(!checkChainValidity()){
            log.error("Blockchain is not valid");
            blockChain.remove(block);
            return false;
        }
        mempoolService.removeTransactions(block.getTransactions());
        log.info("Transactions removed: {}", block.getTransactions());
        return true;
    }

    private boolean checkChainValidity(){
        for(int i = 1; i < blockChain.size(); i++){
            Block currentBlock = blockChain.get(i);
            Block previousBlock = blockChain.get(i - 1);
            if(currentBlock.getHash() == null || previousBlock.getHash() == null){
                log.error("Block hash is null");
                return false;
            }
            if(!isBlockValid(currentBlock, previousBlock)){
                return false;
            }

        }
        log.info("Blockchain is valid");
        return true;
    }
}