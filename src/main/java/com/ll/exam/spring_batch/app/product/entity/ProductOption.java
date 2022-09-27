package com.ll.exam.spring_batch.app.product.entity;

import com.ll.exam.spring_batch.app.base.entity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class ProductOption extends BaseEntity {

    private String color;
    private String size;
    private int price;
    @ManyToOne(fetch = LAZY)
    private Product product;
    private boolean isSoldOut; // 사입처에서의 품절 여부
    private int stockQuantity; // 소핑몰이 보유한 물건 수량 (재고)

    public ProductOption(String color, String size){
        this.color = color;
        this.size = size;
    }
}