package com.example.ecommerce.pms.service;

import com.example.ecommerce.pms.entity.Product;
import com.example.ecommerce.pms.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    public List<Product> getProducts() {
        List<Product> products = productRepository.findAllByOrderByNameAsc();
        products.forEach(product -> {
            product.setImagePath("http://localhost:8080/images/" + product.getId() + ".jpg");
        });
        return products;
    }

    public Product getProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        return product.orElse(null);
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }
}
