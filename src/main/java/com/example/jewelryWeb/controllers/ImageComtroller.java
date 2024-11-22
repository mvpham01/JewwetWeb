package com.example.jewelryWeb.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;
import com.example.jewelryWeb.service.StorageService;
@RestController
@RequestMapping("/image")
public class ImageComtroller {
    @Autowired
	private StorageService service;

	@PostMapping
	public ResponseEntity<?> uploadImage(@RequestParam("image")MultipartFile file) throws IOException {
		String uploadImage = service.uploadImage(file);
		return ResponseEntity.status(HttpStatus.OK)
				.body(uploadImage);
	}

	@GetMapping("/{fileName}")
	public ResponseEntity<?> downloadImage(@PathVariable String fileName){
		byte[] imageData=service.downloadImage(fileName);
		return ResponseEntity.status(HttpStatus.OK)
				.contentType(MediaType.valueOf("image/png"))
				.body(imageData);

	}
	
	@PostMapping("/muti-images")
	public ResponseEntity<?> uploadImages(@RequestParam("images") List<MultipartFile> files) throws IOException {
		List<String> uploadedImages = service.uploadImages(files);
		return ResponseEntity.status(HttpStatus.OK)
				.body(uploadedImages);
}
}
