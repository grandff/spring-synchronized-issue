package com.example.stock.system.stock_system.facade;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import com.example.stock.system.stock_system.service.StockService;

@Component
public class RedissonLockStockFacade {
    private final RedissonClient redissonClient;
    private final StockService stockService;

    public RedissonLockStockFacade(RedissonClient redissonClient, StockService stockService) {
        this.redissonClient = redissonClient;
        this.stockService = stockService;
    }

    public void decrease(Long id, Long quantity) {
        // 락 객체 가져옴
        RLock lock = redissonClient.getLock(id.toString());

        try{
            // 몇 초 동안 락 획득을 시도할지, 점유할지 등 설정해주고 락 획득
            boolean available = lock.tryLock(10, 1, java.util.concurrent.TimeUnit.SECONDS);

            // 실패한 경우에 로그 남김
            if(!available) {
                System.out.println("Failed to acquire lock");
                return;
            }

            stockService.decrease(id, quantity);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }finally{
            // 락해제
            lock.unlock();
        }
    }
}
