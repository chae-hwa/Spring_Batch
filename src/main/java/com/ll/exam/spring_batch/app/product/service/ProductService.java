package com.ll.exam.spring_batch.app.product.service;

import com.ll.exam.spring_batch.app.product.entity.Product;
import com.ll.exam.spring_batch.app.product.repository.ProductRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.Store;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public void create(String name , int price, String makerShopName) {

        Product product = Product.builder()
                .name(name)
                .price(price)
                .makerShopName(makerShopName)
                .build();

        productRepository.save(product);

    }
}
