package com.example.jewelryWeb.service;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.jewelryWeb.models.Entity.Collection;
import com.example.jewelryWeb.repository.CollectionRepository;
@Service
public class CollectionService {
    @Autowired
    private  CollectionRepository collectionRepository;


    // Lấy tất cả collections
    public List<Collection> getAllCollections() {
        return collectionRepository.findAll();
    }

    // Lấy collection theo ID
    public Optional<Collection> getCollectionById(Long collectionId) {
        return collectionRepository.findById(collectionId);
    }

    // Thêm collection mới
    public Collection addCollection(Collection collection) {
        return collectionRepository.save(collection);
    }

    // Cập nhật collection
    public Collection updateCollection(Long collectionId, Collection updatedCollection) {
        return collectionRepository.findById(collectionId).map(collection -> {
            collection.setName(updatedCollection.getName());
            collection.setDescription(updatedCollection.getDescription());
            collection.setImage(updatedCollection.getImage());
            collection.setAvatar(updatedCollection.getAvatar());
            collection.setBanner(updatedCollection.getBanner());
            collection.setIsActive(updatedCollection.getIsActive());
            collection.setProducts(updatedCollection.getProducts());
            return collectionRepository.save(collection);
        }).orElseThrow(() -> new IllegalArgumentException("Collection not found with id: " + collectionId));
    }

    // Xóa collection
    public void deleteCollection(Long collectionId) {
        collectionRepository.deleteById(collectionId);
    }
}