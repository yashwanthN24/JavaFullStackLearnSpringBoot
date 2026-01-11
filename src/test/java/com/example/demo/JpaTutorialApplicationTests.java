package com.example.demo;

import com.example.demo.entites.ProductEntity;
import com.example.demo.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class JpaTutorialApplicationTests {

	@Autowired
	ProductRepository repo;

	@Test
	void contextLoads() {
	}

	@Test
	void testRepository(){
		ProductEntity prod = ProductEntity.builder().sku("Nestles").title("Chocolate").quantity(2).price(BigDecimal.valueOf(23.98)).build();
//		ProductEntity savedProd = this.repo.save(prod);
//		System.out.println(savedProd);
	}

	@Test
	void getRepository(){
//		List<ProductEntity> allProducts = this.repo.findByTitle("Parle-G");
//		System.out.println(allProducts);

		List<ProductEntity> allProductAfterDate = this.repo.findByCreatedAtAfterOrderByTitleDesc(LocalDateTime.of(2024 , 1 , 1 , 0 , 0 , 0));
		System.out.println(allProductAfterDate);

		List<ProductEntity> allProductGreaterthanSpecificQuantityAndPrice = this.repo.findByQuantityGreaterThanOrPriceLessThan(2 , BigDecimal.valueOf(23.0));
		System.out.println(allProductGreaterthanSpecificQuantityAndPrice);

		List<ProductEntity> entities = this.repo.findByTitleContainingIgnoreCase("CHOco" , null);
		System.out.println(entities);

	}

	@Test
	void getSingleFromRepository() {
		Optional<ProductEntity> productEntity = this.repo
				.findByTitleAndPrice("Lime Soda", BigDecimal.valueOf(112.34));
		productEntity.ifPresent(System.out::println);

		Optional<ProductEntity> product = this.repo
				.findBySku("Parles");
		product.ifPresent(System.out::println);


	}

}
