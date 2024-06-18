package com.example.stock.system.stock_system.facade;

import org.springframework.stereotype.Component;

import com.example.stock.system.stock_system.repository.LockRepository;
import com.example.stock.system.stock_system.service.StockService;

@Component
public class NamedLockStockFacade {
    private final LockRepository lockRepository;
    private final StockService stockService;

    public NamedLockStockFacade(LockRepository lockRepository, StockService stockService) {
        this.lockRepository = lockRepository;
        this.stockService = stockService;
    }

    public void decrease(Long id, Long quantity) {
        while (true) {
            try {
                lockRepository.getLock(id.toString());
                stockService.decrease(id, quantity);
                break;
            } finally {
                lockRepository.releaseLock(id.toString());
            }
        }
    }
}
