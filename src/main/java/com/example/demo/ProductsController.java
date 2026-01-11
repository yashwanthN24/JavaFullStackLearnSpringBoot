package com.example.demo;

import com.example.demo.entites.ProductEntity;
import com.example.demo.repositories.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "products")
public class ProductsController {

    private final Integer PAGE_SIZE = 5;

    private final ProductRepository prodRepo;

    public ProductsController(ProductRepository prodRepo) {
        this.prodRepo = prodRepo;
    }

    @GetMapping()
    List<ProductEntity> getAllProducts(){
        return this.prodRepo.findByOrderByTitle();
    }

    @GetMapping("/all")
    List<ProductEntity> getAllProductsWithSortAndPagination(@RequestParam(defaultValue = "") String title , @RequestParam(defaultValue = "id") String sortBy , @RequestParam(defaultValue = "0") Integer pagenumber){
//        return this.prodRepo.findBy(Sort.by(sortB));// this is bydefault ascending order

//          return this.prodRepo.findBy((Sort.by(Sort.Direction.DESC , sortBy , "price"))); // if same sort by price in desc
//        return this.prodRepo.findBy(Sort.by(Sort.Direction.ASC , sortBy , "title" , "quantity"));

//    return this.prodRepo.findBy(Sort.by( Sort.Order.desc(sortBy) , Sort.Order.desc("title")  ));//        Much flexible way  if samed sort by desc based on title (Use This more flexxible and easier to understand as well)


//        pagination
//        Pageable pageable = PageRequest.of(pagenumber , PAGE_SIZE , Sort.by(sortBy));
//        return this.prodRepo.findAll(pageable);


//        can apply pagable to exitsing query method as well and return as List as well
        return this.prodRepo.findByTitleContainingIgnoreCase(title , PageRequest.of(pagenumber , PAGE_SIZE , Sort.by(Sort.Order.asc(sortBy) , Sort.Order.desc("price") , Sort.Order.asc("id"))));
    }
}
