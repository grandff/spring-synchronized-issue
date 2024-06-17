package com.example.stock.system.stock_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.stock.system.stock_system.domain.Stock;

public interface StockRepository extends JpaRepository<Stock, Long>{
    
}
