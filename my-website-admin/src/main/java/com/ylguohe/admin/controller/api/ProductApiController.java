package com.ylguohe.admin.controller.api;

import com.ylguohe.admin.entity.Product;
import com.ylguohe.admin.entity.VehicleDestination;
import com.ylguohe.admin.entity.enums.ProductCategory;
import com.ylguohe.admin.service.ProductService;
import com.ylguohe.admin.service.VehicleDestinationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductApiController {

    private final ProductService productService;
    private final VehicleDestinationService vehicleDestinationService;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts(@RequestParam(required = false) String category) {
        if (category != null) {
            return ResponseEntity.ok(productService.findActiveByCategory(ProductCategory.valueOf(category.toUpperCase())));
        }
        return ResponseEntity.ok(productService.findAllActive());
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    @GetMapping("/vehicles/destinations")
    public ResponseEntity<List<VehicleDestination>> getVehicleDestinations() {
        return ResponseEntity.ok(vehicleDestinationService.findAllActive());
    }
}
