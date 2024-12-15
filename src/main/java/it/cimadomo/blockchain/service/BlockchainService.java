package it.cimadomo.blockchain.service;

import it.cimadomo.blockchain.model.Block;

import java.util.List;

public interface BlockchainService {
    List<Block> getBlockChain();
    boolean isBlockValid(Block block, Block previousBlock);
    Block getLatestBlock();
    boolean addBlock(Block block);
}
