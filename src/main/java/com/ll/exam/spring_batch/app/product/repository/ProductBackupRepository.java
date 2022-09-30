package com.ll.exam.spring_batch.app.product.repository;

import com.ll.exam.spring_batch.app.product.entity.Product;
import com.ll.exam.spring_batch.app.product.entity.ProductBackup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductBackupRepository extends JpaRepository<Product, Long> {
    Optional<ProductBackup> findByProductId(Long id);
}
