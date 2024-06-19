package com.example.stock.system.stock_system.facade;

import org.springframework.stereotype.Component;

import com.example.stock.system.stock_system.repository.RedisLockRepository;
import com.example.stock.system.stock_system.service.StockService;

@Component
public class LettuceLockStockFacade {
    private final RedisLockRepository redisLockRepository;
    private final StockService stockService;

    public LettuceLockStockFacade(RedisLockRepository redisLockRepository, StockService stockService) {
        this.redisLockRepository = redisLockRepository;
        this.stockService = stockService;
    }

    public void decrease(Long id, Long quantity) throws InterruptedException {
        while(!redisLockRepository.lock(id)) {
            // 락 획득에 실패 했으면 100ms 의 텀을 두고 락 획득을 재시도
            // 이렇게 해서 레디스에 가는 부하를 줄여줌
            Thread.sleep(100);
        }

        try{
            stockService.decrease(id, quantity);
        } finally {
            redisLockRepository.unlock(id);
        }
    }
}
