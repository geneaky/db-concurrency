package com.example.stock.facade;

import com.example.stock.repository.RedisRepository;
import com.example.stock.service.StockService;
import org.springframework.stereotype.Component;

@Component
public class LettuceLockStockFacade {

    private final  RedisRepository repository;
    private final  StockService service;

    public LettuceLockStockFacade(RedisRepository repository, StockService service) {
        this.repository = repository;
        this.service = service;
    }

    public void decrease(Long key, Long quantity) throws InterruptedException {
        while(!repository.lock(key)) {
            Thread.sleep(100);
        }

        try {
            service.decrease(key, quantity);
        } finally {
            repository.unlock(key);
        }
    }
}
