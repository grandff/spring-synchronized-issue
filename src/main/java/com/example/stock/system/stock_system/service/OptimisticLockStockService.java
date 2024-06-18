package com.example.stock.system.stock_system.service;

import org.springframework.stereotype.Service;

import com.example.stock.system.stock_system.domain.Stock;
import com.example.stock.system.stock_system.repository.StockRepository;

import jakarta.transaction.Transactional;

@Service
public class OptimisticLockStockService {
    private final StockRepository stockRepository;

    public OptimisticLockStockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Transactional
    public void decrease(Long id, Long quantity) {        
        Stock stock = stockRepository.findByIdWithOptimisticLock(id);
        stock.decrease(quantity);
        stockRepository.save(stock);
    }
}
