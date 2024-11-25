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
    //nhẫn kim cương nam
    @GetMapping("ring")
    public ResponseEntity<List<Product>> getAllRing() {
        return ResponseEntity.ok(productService.getProductsByCategoryId((long)1));
    }
    //nhẫn kim cương nam
    @GetMapping("ring/male")
    public ResponseEntity<List<Product>> getAllMaleRing() {
        return ResponseEntity.ok(productService.getProductsByCategoryId((long)2));
    }
    //nhẫn kim cương nữ
    @GetMapping("ring/female")
    public ResponseEntity<List<Product>> getAllFeMaleRing() {
        return ResponseEntity.ok(productService.getProductsByCategoryId((long)3));
    }
    //nhẫn cầu hôn
    @GetMapping("ring/proposal")
    public ResponseEntity<List<Product>> getAllProposalRing() {
        return ResponseEntity.ok(productService.getProductsByCategoryId((long)4));
    }
    //nhẫn cưới 
    @GetMapping("ring/wedding")
    public ResponseEntity<List<Product>> getAllWeddingRing() {
        return ResponseEntity.ok(productService.getProductsByCategoryId((long)5));
    }
    //vòng tay
    @GetMapping("bracelet")
    public ResponseEntity<List<Product>> getAllBracelet() {
        return ResponseEntity.ok(productService.getProductsByCategoryId((long)6));
    }
    //vòng cổ
    @GetMapping("necklace")
    public ResponseEntity<List<Product>> getAllNecklace() {
        return ResponseEntity.ok(productService.getProductsByCategoryId((long)7));
    }
    //bông tai
    @GetMapping("earrings")
    public ResponseEntity<List<Product>> getAllEarrings() {
        return ResponseEntity.ok(productService.getProductsByCategoryId((long)8));
    }
    //bộ trang sức
    @GetMapping("jewelryset")
    public ResponseEntity<List<Product>> getAlljewelrySet() {
        return ResponseEntity.ok(productService.getProductsByCategoryId((long)9));
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Product>> filterProducts(
            @RequestParam(required = false) String material,
            @RequestParam(required = false) String metallicColor,
            @RequestParam(required = false) Gender gender,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice) {
        List<Product> products = productService.filterProducts(material, metallicColor, gender, minPrice, maxPrice);
        return ResponseEntity.ok(products);
    }
}
