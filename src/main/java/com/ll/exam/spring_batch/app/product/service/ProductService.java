package com.ll.exam.spring_batch.app.product.service;

import com.ll.exam.spring_batch.app.product.entity.Product;
import com.ll.exam.spring_batch.app.product.entity.ProductOption;
import com.ll.exam.spring_batch.app.product.repository.ProductRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.Store;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Product create(String name , int salePrice, int wholesalePrice, String makerShopName, List<ProductOption> options) {

        int price = (int)Math.ceil(wholesalePrice * 1.6) / 100 * 100; //소비자가(price) = 도매가 * 1.6

        Product product = Product.builder()
                .name(name)
                .salePrice(salePrice)
                .price(price)
                .wholesalePrice(wholesalePrice)
                .makerShopName(makerShopName)
                .build();

        for(ProductOption option : options ){
            product.addOption(option);
        }

        productRepository.save(product);

        return product;
    }
}
