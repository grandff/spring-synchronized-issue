package com.example.stock.system.stock_system.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import lombok.Data;

@Entity
@Data
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;   

    private Long productId;
    
    private Long quantity;

    @Version
    private Long version;

    public Stock(){
    }

    public Stock(Long productId, Long quantity){
        this.productId = productId;
        this.quantity = quantity;
    }

    public Long getQuantity(){
        return quantity;
    }

    public void decrease(Long quantity){
        if(this.quantity - quantity < 0){
            throw new IllegalArgumentException("재고가 부족합니다.");
        }
        this.quantity -= quantity;
    }
}
