package com.example.jewelryWeb.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.jewelryWeb.models.DTO.ProductDTO;
import com.example.jewelryWeb.models.DTO.ProductEditDTO;
import com.example.jewelryWeb.models.Entity.Category;
import com.example.jewelryWeb.models.Entity.Gender;
import com.example.jewelryWeb.models.Entity.ImageData;
import com.example.jewelryWeb.models.Entity.Product;
import com.example.jewelryWeb.repository.CategoryRepository;
import com.example.jewelryWeb.repository.ProductRepository;
import com.example.jewelryWeb.repository.StorageRepository;
import com.example.jewelryWeb.util.ImageUtils;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private StorageRepository imageDataRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long productId) {
        return productRepository.findById(productId);
    }

    public Product addProduct(Product product) {
        return productRepository.save(product);
    }
    public List<Product> searchByName(String name) {
        return productRepository.findByProductNameContainingIgnoreCase(name);
    }
    public Product createProduct(ProductDTO productDTO) throws IOException {
        if (isProductNameDuplicate(productDTO.getProductName())) {
            throw new IllegalArgumentException("Product name already exists: " + productDTO.getProductName());
        }
        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Category not found with ID: " + productDTO.getCategoryId()));
        List<String> imageIds = new ArrayList<>();
        if (productDTO.getImages() != null) {
            for (MultipartFile file : productDTO.getImages()) {
                ImageData imageData = imageDataRepository.save(
                        ImageData.builder()
                                .name(file.getOriginalFilename())
                                .type(file.getContentType())
                                .imageData(ImageUtils.compressImage(file.getBytes()))
                                .build());
                imageIds.add(imageData.getId().toString());
            }
        }

        Product product = Product.builder()
                .productName(productDTO.getProductName())
                .category(category)
                .price(productDTO.getPrice())
                .metallicColor(productDTO.getMetallicColor())
                .ringBelt(productDTO.getRingBelt())
                .material(productDTO.getMaterial())
                .discount(productDTO.getDiscount())
                .images(String.join(",", imageIds))
                .isFeatured(productDTO.getIsFeatured())
                .isActive(productDTO.getIsActive())
                .build();

        return productRepository.save(product);
    }

    public void deleteProductById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + productId));
        if (product.getCollections() != null && !product.getCollections().isEmpty()) {
            throw new IllegalStateException("Product is part of a collection and cannot be deleted.");
        }
        if (product.getImages() != null && !product.getImages().isEmpty()) {
            String[] imageIds = product.getImages().split(",");
            for (String imageId : imageIds) {
                imageDataRepository.deleteById(Long.valueOf(imageId));
            }
        }
        productRepository.delete(product);
    }

    // public Product editProduct(Long productId, ProductDTO productDTO) throws IOException {
    //     Product product = productRepository.findById(productId)
    //             .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + productId));
    //     if (!product.getProductName().equals(productDTO.getProductName()) &&
    //             isProductNameDuplicate(productDTO.getProductName())) {
    //         throw new IllegalArgumentException("Product name already exists: " + productDTO.getProductName());
    //     }
    //     Set<ImageData> currentImages = new HashSet<>();
    //     if (product.getImages() != null && !product.getImages().isEmpty()) {
    //         List<Long> currentImageIds = Arrays.stream(product.getImages().split(","))
    //                 .map(Long::valueOf)
    //                 .toList();
    //         currentImages.addAll(imageDataRepository.findAllById(currentImageIds));
    //     }
    //     Set<ImageData> newImages = new HashSet<>();
    //     Set<String> newImageNames = new HashSet<>();
    //     if (productDTO.getImages() != null) {
    //         for (MultipartFile file : productDTO.getImages()) {
    //             String newName = file.getOriginalFilename();
    //             newImageNames.add(newName);
    //             Optional<ImageData> existingImage = currentImages.stream()
    //                     .filter(img -> img.getName().equals(newName))
    //                     .findFirst();

    //             if (existingImage.isPresent()) {
    //                 newImages.add(existingImage.get());
    //             } else {
    //                 ImageData newImage = imageDataRepository.save(
    //                         ImageData.builder()
    //                                 .name(newName)
    //                                 .type(file.getContentType())
    //                                 .imageData(ImageUtils.compressImage(file.getBytes()))
    //                                 .build());
    //                 newImages.add(newImage);
    //             }
    //         }
    //     }
    //     for (ImageData currentImage : currentImages) {
    //         if (!newImageNames.contains(currentImage.getName())) {
    //             boolean isReferenced = productRepository.existsByImageId(currentImage.getId().toString());
    //             if (isReferenced) {
    //                 imageDataRepository.deleteById(currentImage.getId());
    //             }
    //         }
    //     }

    //     product.setProductName(productDTO.getProductName());
    //     product.setCategory(
    //             categoryRepository.findById(productDTO.getCategoryId())
    //                     .orElseThrow(() -> new IllegalArgumentException(
    //                             "Category not found with ID: " + productDTO.getCategoryId())));
    //     product.setPrice(productDTO.getPrice());
    //     product.setMetallicColor(productDTO.getMetallicColor());
    //     product.setRingBelt(productDTO.getRingBelt());
    //     product.setMaterial(productDTO.getMaterial());
    //     product.setDiscount(productDTO.getDiscount());
    //     Set<String> finalImageIds = newImages.stream()
    //             .map(image -> String.valueOf(image.getId()))
    //             .collect(Collectors.toSet());
    //     product.setImages(String.join(",", finalImageIds));

    //     product.setIsFeatured(productDTO.getIsFeatured());
    //     product.setIsActive(productDTO.getIsActive());
    //     return productRepository.save(product);
    // }
    public Product editProduct(Long productId, ProductEditDTO productDTO) throws IOException {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + productId));
    
        if (!product.getProductName().equals(productDTO.getProductName()) &&
                isProductNameDuplicate(productDTO.getProductName())) {
            throw new IllegalArgumentException("Product name already exists: " + productDTO.getProductName());
        }
    
        Set<ImageData> currentImages = new HashSet<>();
        if (product.getImages() != null && !product.getImages().isEmpty()) {
            List<Long> currentImageIds = Arrays.stream(product.getImages().split(","))
                    .map(Long::valueOf)
                    .collect(Collectors.toList());
            currentImages.addAll(imageDataRepository.findAllById(currentImageIds));
        }
    
        Set<String> newImageNames = new HashSet<>();
        Set<ImageData> newImages = new HashSet<>();
    
        if (productDTO.getImages() != null) {
            for (MultipartFile file : productDTO.getImages()) {
                String newName = file.getOriginalFilename();
                newImageNames.add(newName);
    
                Optional<ImageData> existingImage = currentImages.stream()
                        .filter(img -> img.getName().equals(newName))
                        .findFirst();
    
                if (existingImage.isPresent()) {
                    newImages.add(existingImage.get());
                } else {
                    ImageData newImage = imageDataRepository.save(
                            ImageData.builder()
                                    .name(newName)
                                    .type(file.getContentType())
                                    .imageData(ImageUtils.compressImage(file.getBytes()))
                                    .build());
                    newImages.add(newImage);
                }
            }
        }
    
        for (ImageData currentImage : currentImages) {
            if (!newImageNames.contains(currentImage.getName()) && 
                !productDTO.getExistingImages().contains(currentImage.getId())) {
                imageDataRepository.deleteById(currentImage.getId());
            }
        }
    
        Set<String> finalImageIds = new HashSet<>(productDTO.getExistingImages().stream()
                .map(String::valueOf)
                .collect(Collectors.toSet()));
    
        finalImageIds.addAll(newImages.stream()
                .map(image -> String.valueOf(image.getId()))
                .collect(Collectors.toSet()));
    
        product.setImages(String.join(",", finalImageIds));
        product.setProductName(productDTO.getProductName());
        product.setCategory(
                categoryRepository.findById(productDTO.getCategoryId())
                        .orElseThrow(() -> new IllegalArgumentException(
                                "Category not found with ID: " + productDTO.getCategoryId())));
        product.setPrice(productDTO.getPrice());
        product.setMetallicColor(productDTO.getMetallicColor());
        product.setRingBelt(productDTO.getRingBelt());
        product.setMaterial(productDTO.getMaterial());
        product.setDiscount(productDTO.getDiscount());
        product.setIsFeatured(productDTO.getIsFeatured());
        product.setIsActive(productDTO.getIsActive());
    
        return productRepository.save(product);
    }
    public List<Product> filterProducts(String material, String metallicColor, Gender gender, BigDecimal minPrice,
            BigDecimal maxPrice) {
        Specification<Product> spec = Specification.where(ProductSpecification.hasMaterial(material))
                .and(ProductSpecification.hasMetallicColor(metallicColor))
                .and(ProductSpecification.hasGender(gender))
                .and(ProductSpecification.isWithinPriceRange(minPrice, maxPrice));

        return productRepository.findAll(spec);
    }

    public List<Product> getProductsByCategoryId(Long categoryId) {
        return productRepository.findByCategory_CategoryId(categoryId);
    }

    public List<Product> filterProducts(Long categoryId, BigDecimal minPrice, BigDecimal maxPrice, String material,
            String metallicColor, Boolean gender) {
        return productRepository.filterProducts(categoryId, minPrice, maxPrice, material, metallicColor, gender);
    }

    public boolean isProductNameDuplicate(String productName) {
        return productRepository.existsByProductName(productName);
    }

    public Optional<Product> findById(Long id) {
       return productRepository.findById(id);
    }

}