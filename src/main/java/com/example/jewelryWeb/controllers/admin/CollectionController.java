package com.example.jewelryWeb.controllers.admin;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.example.jewelryWeb.models.Entity.*;
import com.example.jewelryWeb.service.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/collections")
public class CollectionController {
    @Autowired
    private CollectionService collectionService;



     @GetMapping
    public ResponseEntity<List<Collection>> getAllCollections() {
        List<Collection> collections = collectionService.getAllCollections();
        return ResponseEntity.ok(collections);
    }

    // Tạo mới một collection
    // @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    // public ResponseEntity<Collection> createCollection(@ModelAttribute CollectionDTO collectionDTO) {
    //     Collection savedCollection = collectionService.saveCollection(collectionDTO);
    //     return ResponseEntity.ok(savedCollection);
    // }

    // Cập nhật collection
    // @PutMapping("/{id}")
    // public ResponseEntity<CollectionDTO> updateCollection(@PathVariable Long id, @RequestBody CollectionDTO collectionDTO) {
    //     Collection updatedCollection = collectionService.updateCollection(id, collectionDTO);
    //     return updatedCollection != null ? ResponseEntity.ok(collectionService.toDTO(updatedCollection)) : ResponseEntity.notFound().build();
    // }

    // Lưu ảnh
    // @PostMapping("/upload-image")
    // public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
    //     String response = storageService.uploadImage(file);
    //     return ResponseEntity.ok(response);
    // }
}