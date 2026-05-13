package com.ylguohe.admin.service;

import com.ylguohe.admin.entity.Product;
import com.ylguohe.admin.entity.enums.ProductCategory;
import com.ylguohe.admin.exception.ResourceNotFoundException;
import com.ylguohe.admin.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;

    public List<Product> findAll() {
        return repository.findAllByOrderBySortOrder();
    }

    public List<Product> findByCategory(ProductCategory category) {
        return repository.findByCategoryOrderBySortOrder(category);
    }

    public List<Product> findActiveByCategory(ProductCategory category) {
        return repository.findByCategoryAndActiveTrueOrderBySortOrder(category);
    }

    public List<Product> findAllActive() {
        return repository.findByActiveTrueOrderBySortOrder();
    }

    public Product findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", id));
    }

    public Product save(Product product) {
        return repository.save(product);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public long count() {
        return repository.count();
    }
}
