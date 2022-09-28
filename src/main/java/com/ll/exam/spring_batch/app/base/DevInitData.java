package com.ll.exam.spring_batch.app.base;

import com.ll.exam.spring_batch.app.cart.service.CartService;
import com.ll.exam.spring_batch.app.member.entity.Member;
import com.ll.exam.spring_batch.app.member.service.MemberService;
import com.ll.exam.spring_batch.app.order.entity.Order;
import com.ll.exam.spring_batch.app.order.service.OrderService;
import com.ll.exam.spring_batch.app.product.entity.Product;
import com.ll.exam.spring_batch.app.product.entity.ProductOption;
import com.ll.exam.spring_batch.app.product.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;

@Configuration
@Profile("dev")
@Slf4j
public class DevInitData {

    @Bean
    public CommandLineRunner initData(MemberService memberService, ProductService productService, CartService cartService, OrderService orderService){
        return args ->
        {
            String password = "{noop}1234";
            Member member1 = memberService.join("user1", password, "user1@test.com");
            Member member2 = memberService.join("user2", password, "user2@test.com");
            Member member3 = memberService.join("user3", password, "user3@test.com");
            Member member4 = memberService.join("user4", password, "user4@test.com");

            memberService.addCash(member1, 10_000, "충전_무통장입금"); // 1만원 충전
            memberService.addCash(member1, 20_000, "충전_무통장입금"); // 2만원 충전
            memberService.addCash(member1, -5_000, "출금_일반"); // 5천원 사용
            memberService.addCash(member1, 300_000, "충전_무통장입금"); // 30만원 충전

            // 해당 회원이 보유 중인 캐시 금액
            long restCash = memberService.getRestCash(member1);

            log.debug("member1 restCash : " + restCash);

            Product product1 = productService.create("단가라 OPS", 68000, 45000, "청평화 A-1-15", Arrays.asList(new ProductOption("RED", "44"), new ProductOption("RED", "55"), new ProductOption("BLUE", "44"), new ProductOption("BLUE", "55")));
            Product product2 = productService.create("쉬폰 OPS", 72000, 55000,"청평화 A-1-15", Arrays.asList(new ProductOption("BLACK", "44"), new ProductOption("BLACK", "55"), new ProductOption("WHITE", "44"), new ProductOption("WHITE", "55")));

            ProductOption product1Option__RED_44  = product1.getProductOptions().get(0);
            ProductOption product1Option__BLUE_44  = product1.getProductOptions().get(2);

            cartService.addItem(member1, product1Option__RED_44, 1);
            cartService.addItem(member1, product1Option__RED_44, 2);
            cartService.addItem(member1, product1Option__BLUE_44 , 1);

            Order order1 = orderService.createFromCart(member1); // member1의 장바구니에서 얻어오겠다.

            int order1PayPrice = order1.calculatePayPrice(); // 주문1의 계산 금액
            orderService.payByRestCashOnly(order1); // 캐시 결제

            /* 고객2의 주문2 */
            ProductOption product2Option__BLACK_44 = product2.getProductOptions().get(0);
            ProductOption product2Option__WHITE_44 = product2.getProductOptions().get(2);

            cartService.addItem(member2, product1Option__RED_44, 1); // 고객2 장바구니에 상품 담기
            cartService.addItem(member2,product2Option__BLACK_44, 2);
            cartService.addItem(member2,product2Option__WHITE_44, 2);

            Order order2 = orderService.createFromCart(member2); //고객2의 장바구니 상품 가져오기

            memberService.addCash(member2, 370_000,"충전_무통장입금");

            orderService.payByRestCashOnly(order2); // 주문2 캐시로 결제하기

        };
    }
}
