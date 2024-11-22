package com.example.jewelryWeb.service;

import java.math.BigDecimal;

import org.springframework.data.jpa.domain.Specification;

import com.example.jewelryWeb.models.Entity.Gender;
import com.example.jewelryWeb.models.Entity.Product;

public class ProductSpecification {

    public static Specification<Product> hasMaterial(String material) {
        return (root, query, cb) -> material == null || material.isEmpty()
                ? null : cb.equal(root.get("material"), material);
    }

    public static Specification<Product> hasMetallicColor(String metallicColor) {
        return (root, query, cb) -> metallicColor == null || metallicColor.isEmpty()
                ? null : cb.equal(root.get("metallicColor"), metallicColor);
    }

    public static Specification<Product> hasGender(Gender gender) {
        return (root, query, cb) -> gender == null
                ? null : cb.equal(root.join("category").get("gender"), gender);
    }

    public static Specification<Product> isWithinPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return (root, query, cb) -> {
            if (minPrice == null && maxPrice == null) return null;
            if (minPrice != null && maxPrice != null) return cb.between(root.get("price"), minPrice, maxPrice);
            if (minPrice != null) return cb.greaterThanOrEqualTo(root.get("price"), minPrice);
            return cb.lessThanOrEqualTo(root.get("price"), maxPrice);
        };
    }
}
