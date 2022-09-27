package com.ll.exam.spring_batch.app.order.service;

import com.ll.exam.spring_batch.app.cart.entity.CartItem;
import com.ll.exam.spring_batch.app.cart.repository.CartItemRepository;
import com.ll.exam.spring_batch.app.cart.service.CartService;
import com.ll.exam.spring_batch.app.member.entity.Member;
import com.ll.exam.spring_batch.app.order.entity.Order;
import com.ll.exam.spring_batch.app.order.entity.OrderItem;
import com.ll.exam.spring_batch.app.order.repository.OrderRepository;
import com.ll.exam.spring_batch.app.product.entity.ProductOption;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.WebResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {
    private final CartService cartService; // service 간의 양방향 참조는 좋지 않음 (cart에선 order 언급x)

    private final OrderRepository orderRepository;
    private final CartItemRepository cartItemRepository;

    @Transactional
    public Order createFromCart(Member member) {
        // 해당 회원의 장바구니 정보를 모두 가져온다.

        // 특정 바구니의 상품 옵션이 판매 불가능 -> 삭제
        // 특정 바구니의 상품 옵션이 판매 가능 -> 주문 품목으로 이동 후 삭제
        List<CartItem> cartItems =  cartService.getItemsByMember(member);

        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem cartItem : cartItems) {
            ProductOption productOption = cartItem.getProductOption(); // 장바구니에서 정보 가져오기

            if(productOption.isOrderable(cartItem.getQuantity())) {
                orderItems.add(new OrderItem(productOption, cartItem.getQuantity()));
            }

            cartService.deleteItem(cartItem);
        }

        return create(member, orderItems);
    }

    @Transactional
    public Order create(Member member, List<OrderItem> orderItems){
        Order order = Order.builder()
                .member(member)
                .build();

        for(OrderItem orderItem : orderItems){
            order.addOrderItem(orderItem);
        }
        orderRepository.save(order);

        return order;
    }
}
