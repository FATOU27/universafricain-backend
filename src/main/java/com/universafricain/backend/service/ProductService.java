package com.universafricain.backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.universafricain.backend.dto.ProductDTO;
import com.universafricain.backend.model.Product;
import com.universafricain.backend.repository.CategoryRepository;
import com.universafricain.backend.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    // Tous les produits disponibles
    public List<ProductDTO> getAllAvailable() {
        return productRepository.findByIsAvailableTrue()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Produits par catégorie
    public List<ProductDTO> getByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Un produit par id
    public ProductDTO getById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produit introuvable"));
        return toDTO(product);
    }

    // Créer un produit (admin)
    public ProductDTO create(ProductDTO dto) {
        Product product = Product.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .stock(dto.getStock())
                .imageUrl(dto.getImageUrl())
                .origine(dto.getOrigine())
                .isAvailable(true)
                .category(categoryRepository.findById(
                        getCategoryIdFromSlug(dto.getCategorySlug()))
                        .orElseThrow(() -> new RuntimeException("Catégorie introuvable")))
                .build();
        return toDTO(productRepository.save(product));
    }

    // Modifier un produit (admin)
    public ProductDTO update(Long id, ProductDTO dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produit introuvable"));
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());
        product.setImageUrl(dto.getImageUrl());
        product.setOrigine(dto.getOrigine());
        product.setIsAvailable(dto.getIsAvailable());
        return toDTO(productRepository.save(product));
    }

    // Supprimer un produit (admin)
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    // Mapper Product → ProductDTO
    private ProductDTO toDTO(Product p) {
        return ProductDTO.builder()
                .id(p.getId())
                .name(p.getName())
                .description(p.getDescription())
                .price(p.getPrice())
                .stock(p.getStock())
                .imageUrl(p.getImageUrl())
                .origine(p.getOrigine())
                .isAvailable(p.getIsAvailable())
                .categoryName(p.getCategory() != null ? p.getCategory().getName() : null)
                .categorySlug(p.getCategory() != null ? p.getCategory().getSlug() : null)
                .build();
    }

    private Long getCategoryIdFromSlug(String slug) {
        return categoryRepository.findBySlug(slug)
                .orElseThrow(() -> new RuntimeException("Slug introuvable"))
                .getId();
    }
}