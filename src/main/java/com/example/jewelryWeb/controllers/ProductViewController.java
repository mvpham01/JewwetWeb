package com.example.jewelryWeb.controllers;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.example.jewelryWeb.models.Entity.*;
import com.example.jewelryWeb.service.*;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/product")
public class ProductViewController {
    @Autowired
    private ProductService productService;
    //nhẫn kim cương 
    @GetMapping("ring")
    public ResponseEntity<List<Product>> getAllRing() {
        return ResponseEntity.ok(productService.getProductsByCategoryId((long)1));
    }
    private ResponseEntity<List<Product>> filterProducts(
            Long categoryId,
            Integer minPriceInMillions,
            Integer maxPriceInMillions,
            String material,
            String metallicColor,
            Boolean gender
    ) {
        BigDecimal minPrice = (minPriceInMillions != null) ? BigDecimal.valueOf(minPriceInMillions * 1_000_000) : null;
        BigDecimal maxPrice = (maxPriceInMillions != null) ? BigDecimal.valueOf(maxPriceInMillions * 1_000_000) : null;
        List<Product> products = productService.filterProducts(categoryId, minPrice, maxPrice, material, metallicColor, gender);
        return ResponseEntity.ok(products);
    }

    /**
     * Nhẫn kim cương nam
     */
    @GetMapping("/ring/male")
    public ResponseEntity<List<Product>> getAllMaleRing(
            @RequestParam(required = false) Integer minPriceInMillions,
            @RequestParam(required = false) Integer maxPriceInMillions,
            @RequestParam(required = false) String material,
            @RequestParam(required = false) String metallicColor,
            @RequestParam(required = false) Boolean gender
    ) {
        return filterProducts(1L, minPriceInMillions, maxPriceInMillions, material, metallicColor, gender != null ? gender : true);
    }

    /**
     * Nhẫn kim cương nữ
     */
    @GetMapping("/ring/female")
    public ResponseEntity<List<Product>> getAllFeMaleRing(
            @RequestParam(required = false) Integer minPriceInMillions,
            @RequestParam(required = false) Integer maxPriceInMillions,
            @RequestParam(required = false) String material,
            @RequestParam(required = false) String metallicColor,
            @RequestParam(required = false) Boolean gender
    ) {
        return filterProducts(1L, minPriceInMillions, maxPriceInMillions, material, metallicColor, gender != null ? gender : false);
    }

    /**
     * Nhẫn cầu hôn
     */
    @GetMapping("/ring/proposal")
    public ResponseEntity<List<Product>> getAllProposalRing(
            @RequestParam(required = false) Integer minPriceInMillions,
            @RequestParam(required = false) Integer maxPriceInMillions,
            @RequestParam(required = false) String material,
            @RequestParam(required = false) String metallicColor
    ) {
        return filterProducts(2L, minPriceInMillions, maxPriceInMillions, material, metallicColor, null);
    }

    /**
     * Nhẫn cưới
     */
    @GetMapping("/ring/wedding")
    public ResponseEntity<List<Product>> getAllWeddingRing(
            @RequestParam(required = false) Integer minPriceInMillions,
            @RequestParam(required = false) Integer maxPriceInMillions,
            @RequestParam(required = false) String material,
            @RequestParam(required = false) String metallicColor
    ) {
        return filterProducts(3L, minPriceInMillions, maxPriceInMillions, material, metallicColor, null);
    }

    /**
     * Vòng tay
     */
    @GetMapping("/bracelet")
    public ResponseEntity<List<Product>> getAllBracelet(
            @RequestParam(required = false) Integer minPriceInMillions,
            @RequestParam(required = false) Integer maxPriceInMillions,
            @RequestParam(required = false) String material,
            @RequestParam(required = false) String metallicColor
    ) {
        return filterProducts(4L, minPriceInMillions, maxPriceInMillions, material, metallicColor, null);
    }

    /**
     * Vòng cổ
     */
    @GetMapping("/necklace")
    public ResponseEntity<List<Product>> getAllNecklace(
            @RequestParam(required = false) Integer minPriceInMillions,
            @RequestParam(required = false) Integer maxPriceInMillions,
            @RequestParam(required = false) String material,
            @RequestParam(required = false) String metallicColor
    ) {
        return filterProducts(5L, minPriceInMillions, maxPriceInMillions, material, metallicColor, null);
    }

    /**
     * Bông tai
     */
    @GetMapping("/earrings")
    public ResponseEntity<List<Product>> getAllEarrings(
            @RequestParam(required = false) Integer minPriceInMillions,
            @RequestParam(required = false) Integer maxPriceInMillions,
            @RequestParam(required = false) String material,
            @RequestParam(required = false) String metallicColor
    ) {
        return filterProducts(6L, minPriceInMillions, maxPriceInMillions, material, metallicColor,null);
    }

    /**
     * Bộ trang sức
     */
    @GetMapping("/jewelryset")
    public ResponseEntity<List<Product>> getAllJewelrySet(
            @RequestParam(required = false) Integer minPriceInMillions,
            @RequestParam(required = false) Integer maxPriceInMillions
    ) {
        return filterProducts(7L, minPriceInMillions, maxPriceInMillions,null,null,null);
    }

    // @GetMapping("/filter")
    // public ResponseEntity<List<Product>> filterProducts(
    //         @RequestParam(required = false) String material,
    //         @RequestParam(required = false) String metallicColor,
    //         @RequestParam(required = false) Gender gender,
    //         @RequestParam(required = false) BigDecimal minPrice,
    //         @RequestParam(required = false) BigDecimal maxPrice) {
    //     List<Product> products = productService.filterProducts(material, metallicColor, gender, minPrice, maxPrice);
    //     return ResponseEntity.ok(products);
    // }
}
