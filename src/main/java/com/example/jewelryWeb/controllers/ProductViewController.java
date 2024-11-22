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
    @GetMapping("ring/male")
    public ResponseEntity<List<Product>> getAllMaleRing(@RequestParam String param) {
        return ResponseEntity.ok(null);
    }
    //nhẫn kim cương nữ
    @GetMapping("ring/female")
    public ResponseEntity<List<Product>> getAllFeMaleRing(@RequestParam String param) {
        return ResponseEntity.ok(null);
    }
    //nhẫn cầu hôn
    @GetMapping("ring/proposal")
    public ResponseEntity<List<Product>> getAllProposalRing(@RequestParam String param) {
        return ResponseEntity.ok(null);
    }
    //nhẫn cưới 
    @GetMapping("ring/wedding")
    public ResponseEntity<List<Product>> getAllWeddingRing(@RequestParam String param) {
        return ResponseEntity.ok(null);
    }
    //vòng tay
    @GetMapping("ring/bracelet")
    public ResponseEntity<List<Product>> getAllBracelet(@RequestParam String param) {
        return ResponseEntity.ok(null);
    }
    //vòng cổ
    @GetMapping("ring/necklace")
    public ResponseEntity<List<Product>> getAllNecklace(@RequestParam String param) {
        return ResponseEntity.ok(null);
    }
    //bông tai
    @GetMapping("ring/earrings")
    public ResponseEntity<List<Product>> getAllEarrings(@RequestParam String param) {
        return ResponseEntity.ok(null);
    }
    //bộ trang sức
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
