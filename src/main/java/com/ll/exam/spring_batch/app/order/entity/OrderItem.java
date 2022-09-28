package com.ll.exam.spring_batch.app.order.entity;

import com.ll.exam.spring_batch.app.base.entity.BaseEntity;
import com.ll.exam.spring_batch.app.product.entity.ProductOption;
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
public class OrderItem extends BaseEntity {

    @ManyToOne(fetch=LAZY)
    @ToString.Exclude
    private Order order;

    @ManyToOne(fetch = LAZY)
    private ProductOption productOption;

    private int quantity;

    // 후에 정산을 위해 가격 저장
    private int price; // 권장판매가(판매)
    private int salePrice; // 실제판매가(할인)
    private int wholesalePrice; // 도매가(구매)
    public OrderItem(ProductOption productOption, int quantity){
        this.productOption = productOption;
        this.quantity=quantity;
        this.price = productOption.getPrice();
        this.price = productOption.getSalePrice();
        this.price = productOption.getWholesalePrice();
    }

}
