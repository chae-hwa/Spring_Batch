package com.ll.exam.spring_batch.app.order.repository;

import com.ll.exam.spring_batch.app.order.entity.Order;
import com.ll.exam.spring_batch.app.order.entity.OrderItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    // 1 ~ n 개와 같이 범위(chunk)를 가져올 땐 list가 아닌 pageble로 불러와야 된다. (몇 억개가 될 수도)
    // 그래서 List가 아닌 Page를 사용한 것.
    Page<OrderItem> findAllByIdLessThan(long id, Pageable pageable);
    // -> 실행쿼리 : SELECT * FROM order_item AS OI WHERE OI.id < 6;
}
