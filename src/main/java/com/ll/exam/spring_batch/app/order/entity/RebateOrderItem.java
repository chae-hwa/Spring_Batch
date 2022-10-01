package com.ll.exam.spring_batch.app.order.entity;


import com.ll.exam.spring_batch.app.base.entity.BaseEntity;
import com.ll.exam.spring_batch.app.product.entity.ProductOption;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class RebateOrderItem extends BaseEntity {

    @OneToOne(fetch = LAZY)
    @ToString.Exclude
    @JoinColumn(foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private OrderItem orderItem;

    @ManyToOne(fetch = LAZY)
    @ToString.Exclude
    @JoinColumn(foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Order order;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private ProductOption productOption;

    private int quantity;

    private int price;
    private int salePrice;
    private int wholesalePrice;
    private int pgFee;
    private int payPrice;
    private int refundPrice;
    private int refundQuantity;
    private boolean isPaid;

    // RebateOrderItem에 OrderItem 필드를 가져온다.
    public RebateOrderItem(OrderItem orderItem){
        this.orderItem = orderItem;
        order = orderItem.getOrder();
        productOption = orderItem.getProductOption();
        quantity = orderItem.getQuantity();
        price = orderItem.getPrice();
        salePrice = orderItem.getSalePrice();
        wholesalePrice = orderItem.getWholesalePrice();
        pgFee = orderItem.getPgFee();
        payPrice = orderItem.getPayPrice();
        refundPrice = orderItem.getRefundPrice();
        refundQuantity = orderItem.getRefundQuantity();
        isPaid =orderItem.isPaid();
    }

    // RebateOrderItem에 ProductOption 필드를 가져온다.
    public RebateOrderItem(ProductOption productOption){
        this.productOption = productOption;
        this.quantity = quantity;
        this.price = productOption.getPrice();
        this.salePrice = productOption.getSalePrice();
        this.wholesalePrice = productOption.getWholesalePrice();
    }

    public int calculatePrice(){
        return salePrice * quantity;
    }

    public void setPaymentDone(){
        this.pgFee = 0;
        this.payPrice = calculatePrice();
        this.isPaid = true;
    }

    public void setRefundDone() {
        if (refundQuantity == quantity) return;

        this.refundQuantity = quantity;
        this.refundPrice = payPrice;
    }
}
