package com.example.jewelryWeb.repository;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.jewelryWeb.models.Entity.*;
@Repository
public interface ProductRepository extends JpaRepository<Product, Long>,JpaSpecificationExecutor<Product> {
    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM Product p WHERE p.images LIKE CONCAT('%', :imageId, '%')")
    boolean existsByImageId(@Param("imageId") String imageId);
    
    List<Product> findByCategory_CategoryId(Long categoryId);

    @Query("SELECT p FROM Product p WHERE p.id IN :ids")
    List<Product> findAllById(@Param("ids") List<Integer> ids);

    @Query("SELECT p FROM Product p WHERE "
         + "(p.category.categoryId = :categoryId OR :categoryId IS NULL) "
         + "AND (p.price >= :minPrice OR :minPrice IS NULL) "
         + "AND (p.price <= :maxPrice OR :maxPrice IS NULL) "
         + "AND (p.material = :material OR :material IS NULL) "
         + "AND (p.metallicColor = :metallicColor OR :metallicColor IS NULL) "
         + "AND (p.ringBelt = :gender OR :gender IS NULL)")
    List<Product> filterProducts(
        @Param("categoryId") Long categoryId,
        @Param("minPrice") BigDecimal minPrice,
        @Param("maxPrice") BigDecimal maxPrice,
        @Param("material") String material,
        @Param("metallicColor") String metallicColor,
        @Param("gender") String gender
    );
    boolean existsByProductName(String productName);
}