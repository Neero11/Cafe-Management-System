package com.inn.cafe.category.Repository;

import com.inn.cafe.category.model.Product;
import com.inn.cafe.wrapper.ProductWrapper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<ProductWrapper> getAllProduct();
}
