package com.ylguohe.admin.repository;

import com.ylguohe.admin.entity.Product;
import com.ylguohe.admin.entity.enums.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryAndActiveTrueOrderBySortOrder(ProductCategory category);
    List<Product> findByActiveTrueOrderBySortOrder();
    List<Product> findAllByOrderBySortOrder();
    List<Product> findByCategoryOrderBySortOrder(ProductCategory category);
}
