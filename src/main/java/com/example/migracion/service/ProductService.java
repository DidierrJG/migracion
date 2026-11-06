package com.example.migracion.service;

import com.example.migracion.model.Category;
import com.example.migracion.model.Product;
import com.example.migracion.model.Tag;
import com.example.migracion.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;

    public ProductService(ProductRepository productRepository,
                          CategoryRepository categoryRepository,
                          TagRepository tagRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.tagRepository = tagRepository;
    }

    @Transactional(readOnly = true)
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Product getById(Long id) {
        return productRepository.findByIdWithDetails(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + id));
    }

    @Transactional
    public Product create(String name, BigDecimal price, Integer stock, Long categoryId, Set<String> tagNames) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada: " + categoryId));

        Product product = Product.builder()
                .name(name)
                .price(price)
                .stock(stock)
                .category(category)
                .build();

        if (tagNames != null) {
            for (String tagName : tagNames) {
                Tag tag = tagRepository.findByName(tagName)
                        .orElseGet(() -> tagRepository.save(Tag.builder().name(tagName).build()));
                product.addTag(tag);
            }
        }

        return productRepository.save(product);
    }
}
