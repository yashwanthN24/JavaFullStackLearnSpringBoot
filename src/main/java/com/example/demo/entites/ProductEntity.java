package com.example.demo.entites;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(
        name = "product_table",
        uniqueConstraints = {
                @UniqueConstraint(name = "sku_unique" , columnNames =  {"sku"}),
                @UniqueConstraint(name = "title_price_unique" , columnNames = {"product_title" , "price"}) // means combination of title and price  must be unique
        },
        indexes = {
                @Index(name = "sku_index" , columnList = "sku") // bydefault primary key field i.e id  has unique and index for faster retrieval of any specific row in any Table
        }
)
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false , length = 20)
    private String sku;

    @Column(name = "product_title" )
    private String title;

    private BigDecimal price;

    private Integer quantity;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
