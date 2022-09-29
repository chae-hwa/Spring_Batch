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
    private int pgFee; // 결제대행사 수수료
    private int payPrice; // 결제 금액
    private int refundPrice; // 환불 금액
    private int refundQuantity; // 환불 수량
    private boolean isPaid; // 결제 여부
    public OrderItem(ProductOption productOption, int quantity){
        this.productOption = productOption;
        this.quantity = quantity;
        this.price = productOption.getPrice();
        this.salePrice = productOption.getSalePrice();
        this.wholesalePrice = productOption.getWholesalePrice();
    }

    public int calculatePayPrice() {

        return salePrice * quantity; // 계산금액 = 판매가 * 수량
    }

    public void setPaymentDone() {
        this.pgFee = 0; // 캐시 결제 시 수수료 X
        this.payPrice = calculatePayPrice(); // 결제금액 = 계산금액
        this.isPaid = true; // 결제 완료
    }

    public void setRefundDone() {
        if(refundQuantity == quantity ) return; //환불 수량 = 재고 수량

        this.refundQuantity = quantity; // 환불수량 = 결제수량
        this.refundPrice = payPrice; // 환불금액 = 결제금액
    }
}
