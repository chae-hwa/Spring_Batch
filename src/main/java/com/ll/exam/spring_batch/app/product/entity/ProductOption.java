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
    private String displayColor;
    private String displaySize;
    private int price;
    private int salePrice;
    private int wholesalePrice;
    @ManyToOne(fetch = LAZY)
    private Product product;
    private boolean isSoldOut; // 사입처에서의 품절 여부
    private int stockQuantity; // 소핑몰이 보유한 물건 수량 (재고)

    public ProductOption(String color, String size){
        this.color = color;
        this.displayColor = color;
        this.size = size;
        this.displaySize = size;
    }

    public boolean isOrderable(int quantity) {
        if (isSoldOut() == false) return true; // 품절이 아니다 (주문 가능)

        return getStockQuantity() >= quantity; // 쇼핑몰 보유 재고가 주문 수량보다 많다 (주문 가능)
    }
}
