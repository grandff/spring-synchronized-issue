package com.example.stock.system.stock_system.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.stock.system.stock_system.domain.Stock;
import com.example.stock.system.stock_system.repository.StockRepository;

@Service
public class StockService {
    private final StockRepository stockRepository;
    
    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    // synchornized를 붙여서 한개의 스레드만 접근하게 설정 
    // @Transactional
    // public synchronized void decrease(Long id, Long quantity) {
    //     // stock 조회
    //     // 재고를 감소 시킨 뒤
    //     // 갱신된 값을 저장
    //     Stock stock = stockRepository.findById(id).orElseThrow();
    //     stock.decrease(quantity);
    //     stockRepository.save(stock);                        
    // }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void decrease(Long id, Long quantity) {        
        Stock stock = stockRepository.findById(id).orElseThrow();
        stock.decrease(quantity);
        stockRepository.save(stock);                        
    }
}
