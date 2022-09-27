package com.ll.exam.spring_batch.app.product.entity;

import com.ll.exam.spring_batch.app.base.entity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;

@Entity
@Getter
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class Product extends BaseEntity {

    private int price;
    private String name;
    private String makerShopName;
}
