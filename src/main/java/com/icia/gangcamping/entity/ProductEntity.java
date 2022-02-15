package com.icia.gangcamping.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "product_table")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="productId")
    private Long productId;
    @Column
    @NotNull
    private String productName;
    @Column
    @NotNull
    private int productPrice;
    @Column
    @NotNull
    private int productWeight;
    @Column
    @NotNull
    private String productDescription;
    @Column
    @NotNull
    private String productFileName;

    @OneToMany(mappedBy = "productEntity",cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    private List<StockEntity> stockEntityList = new ArrayList<>();
    @OneToMany(mappedBy = "productEntity",cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    private List<CartEntity> cartEntityList = new ArrayList<>();
    @OneToMany(mappedBy = "productEntity",cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    private List<OrderEntity> orderEntityList = new ArrayList<>();
    @OneToMany(mappedBy = "productEntity",cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    private List<ShoppingLikeEntity> shoppingLikeEntityArrayList = new ArrayList<>();
    @OneToMany(mappedBy = "productEntity",cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    private List<QuestionEntity> questionEntityList = new ArrayList<>();

}