package com.example.jewelryWeb.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.jewelryWeb.models.DTO.JemmiaDTO;
import com.example.jewelryWeb.models.Entity.ImageData;
import com.example.jewelryWeb.models.Entity.Jemmia;
import com.example.jewelryWeb.repository.JemmiaRepository;
import com.example.jewelryWeb.repository.StorageRepository;
import com.example.jewelryWeb.util.ImageUtils;

@Service
public class JemmiaService {

    @Autowired
    private JemmiaRepository jemmiaRepository;

    @Autowired
    private StorageRepository imageDataRepository;

    public Jemmia createJemmia(JemmiaDTO jemmiaDTO) throws Exception {
        if (jemmiaRepository.existsByTitle(jemmiaDTO.getTitle())) {
            throw new Exception("Title already exists.");
        }
    
        // Lưu ảnh chính
        ImageData imageData = imageDataRepository.save(
                ImageData.builder()
                        .name(jemmiaDTO.getImage().getOriginalFilename())
                        .type(jemmiaDTO.getImage().getContentType())
                        .imageData(ImageUtils.compressImage(jemmiaDTO.getImage().getBytes()))
                        .build());
    
        // Lưu ảnh thumbnail
        ImageData thumbaliData = imageDataRepository.save(
                ImageData.builder()
                        .name(jemmiaDTO.getThumbali().getOriginalFilename())
                        .type(jemmiaDTO.getThumbali().getContentType())
                        .imageData(ImageUtils.compressImage(jemmiaDTO.getThumbali().getBytes()))
                        .build());
    
        // Tạo đối tượng Jemmia
        Jemmia jemmia = Jemmia.builder()
                .title(jemmiaDTO.getTitle())
                .contentHeader(jemmiaDTO.getContentHeader())
                .contentFooter(jemmiaDTO.getContentFooter())
                .startDate(jemmiaDTO.getStartDate())
                .endDate(jemmiaDTO.getEndDate())
                .publishedAt(jemmiaDTO.getPublishedAt())
                .isActive(jemmiaDTO.getIsActive())
                .image(imageData.getId())
                .thumbali(thumbaliData.getId())
                .build();
    
        return jemmiaRepository.save(jemmia);
    }
    

    public Jemmia updateJemmia(Long id, JemmiaDTO jemmiaDTO) throws Exception {
        Jemmia existingJemmia = jemmiaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Jemmia not found."));
    
        // Kiểm tra nếu title đã thay đổi và tồn tại trong cơ sở dữ liệu
        if (!existingJemmia.getTitle().equals(jemmiaDTO.getTitle()) && jemmiaRepository.existsByTitle(jemmiaDTO.getTitle())) {
            throw new Exception("Title already exists.");
        }
    
        // Xử lý cập nhật ảnh chính
        ImageData imageData = null;
        if (jemmiaDTO.getImage() != null && !jemmiaDTO.getImage().isEmpty()) {
            imageData = imageDataRepository.save(
                    ImageData.builder()
                            .name(jemmiaDTO.getImage().getOriginalFilename())
                            .type(jemmiaDTO.getImage().getContentType())
                            .imageData(ImageUtils.compressImage(jemmiaDTO.getImage().getBytes()))
                            .build());
        }
    
        // Xử lý cập nhật ảnh thumbnail
        ImageData thumbaliData = null;
        if (jemmiaDTO.getThumbali() != null && !jemmiaDTO.getThumbali().isEmpty()) {
            thumbaliData = imageDataRepository.save(
                    ImageData.builder()
                            .name(jemmiaDTO.getThumbali().getOriginalFilename())
                            .type(jemmiaDTO.getThumbali().getContentType())
                            .imageData(ImageUtils.compressImage(jemmiaDTO.getThumbali().getBytes()))
                            .build());
        }
    
        // Cập nhật các trường khác
        existingJemmia.setTitle(jemmiaDTO.getTitle());
        existingJemmia.setContentHeader(jemmiaDTO.getContentHeader());
        existingJemmia.setContentFooter(jemmiaDTO.getContentFooter());
        existingJemmia.setStartDate(jemmiaDTO.getStartDate());
        existingJemmia.setEndDate(jemmiaDTO.getEndDate());
        existingJemmia.setPublishedAt(jemmiaDTO.getPublishedAt());
        existingJemmia.setIsActive(jemmiaDTO.getIsActive());
    
        // Cập nhật ID ảnh chính nếu có thay đổi
        if (imageData != null) {
            existingJemmia.setImage(imageData.getId());
        }
    
        // Cập nhật ID ảnh thumbnail nếu có thay đổi
        if (thumbaliData != null) {
            existingJemmia.setThumbali(thumbaliData.getId());
        }
    
        return jemmiaRepository.save(existingJemmia);
    }
    

    public Jemmia getJemmiaById(Long id) {
        return jemmiaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Jemmia not found."));
    }

    public void deleteJemmia(Long id) {
        Jemmia existingJemmia = jemmiaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Jemmia not found."));
        imageDataRepository.deleteById(existingJemmia.getImage());
        jemmiaRepository.delete(existingJemmia);
    }
}