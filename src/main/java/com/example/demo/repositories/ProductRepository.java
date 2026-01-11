package com.example.demo.repositories;

import com.example.demo.entites.ProductEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository  extends JpaRepository<ProductEntity , Long> {
    List<ProductEntity> findByOrderByTitle();

    List<ProductEntity> findBy(Sort sort);

    List<ProductEntity> findByCreatedAtAfterOrderByTitleDesc(LocalDateTime createdAt);

    List<ProductEntity> findByQuantityGreaterThanOrPriceLessThan(int quantity , BigDecimal price);

    List<ProductEntity> findByTitleContainingIgnoreCase(String chOco , Pageable pageable);

    Optional<ProductEntity> findByTitleAndPrice(String peps, BigDecimal bigDecimal);


    @Query("select  e from ProductEntity e where e.sku=:sku")
    Optional<ProductEntity> findBySku(String sku);
}
