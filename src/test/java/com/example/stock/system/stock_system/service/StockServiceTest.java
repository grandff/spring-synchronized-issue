package com.example.stock.system.stock_system.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.stock.system.stock_system.domain.Stock;
import com.example.stock.system.stock_system.repository.StockRepository;

@SpringBootTest
class StockServiceTest {
    @Autowired
    private StockService stockService;
    @Autowired
    private StockRepository stockRepository;

    // 테스트를 위한 재고 데이터 추가
    // beforeeach annotation 활용
    @BeforeEach
    public void before() {
        stockRepository.saveAndFlush(new Stock(1L, 100L));
    }

    // 테스트가 종료되면 데이터 삭제
    @AfterEach
    public void after() {
        stockRepository.deleteAll();
    }

    // 재고로직 테스트케이스
    @Test
    void 재고감소() {
        stockService.decrease(1L, 1L);

        // 100 -1 = 99
        try{
            Stock stock= stockRepository.findById(1L).orElseThrow();                       
            assertEquals(99L, stock.getQuantity());
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }

    // 동시에 100개의 요청이 들어오는 테스트 케이스
    @Test
    void 동시에_100개의_요청() throws InterruptedException {
        int threadCount = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for(int i=0; i<threadCount; i++){
            executorService.submit(()->{
                try{
                    stockService.decrease(1L, 1L);
                } finally{
                    latch.countDown();
                }
                
            });
        }

        latch.await();

        Stock stock = stockRepository.findById(1L).orElseThrow();
        // 100 - (1 * 100) = 0
        assertEquals(0, stock.getQuantity());
    }
}
