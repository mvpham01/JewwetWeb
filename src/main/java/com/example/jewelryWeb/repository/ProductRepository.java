package com.example.jewelryWeb.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.jewelryWeb.models.Entity.*;
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM Product p WHERE p.images LIKE CONCAT('%', :imageId, '%')")
    boolean existsByImageId(@Param("imageId") String imageId);
    

}