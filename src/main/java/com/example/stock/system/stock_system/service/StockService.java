package com.example.stock.system.stock_system.service;

import org.springframework.stereotype.Service;

import com.example.stock.system.stock_system.domain.Stock;
import com.example.stock.system.stock_system.repository.StockRepository;

import jakarta.transaction.Transactional;

@Service
public class StockService {
    private final StockRepository stockRepository;
    
    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Transactional
    public void decrease(Long id, Long quantity) {
        // stock 조회
        // 재고를 감소 시킨 뒤
        // 갱신된 값을 저장
        Stock stock = stockRepository.findById(id).orElseThrow();
        stock.decrease(quantity);
        stockRepository.save(stock);                        
    }
}
