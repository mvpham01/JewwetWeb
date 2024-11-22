package com.example.jewelryWeb.config;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.jewelryWeb.models.Entity.Category;
import com.example.jewelryWeb.models.Entity.Gender;
import com.example.jewelryWeb.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DatabaseInitializer implements CommandLineRunner {
        private final CategoryRepository categoryRepository;

        @Override
        public void run(String... args) throws Exception {
                // Kiểm tra xem Category "Nhẫn" đã tồn tại chưa
                Optional<Category> rootCategory = categoryRepository.findByCategoryName("Nhẫn");
                if (rootCategory.isEmpty()) {
                        // Tạo category "Nhẫn" và các category con
                        Category root = Category.builder()
                                        .categoryName("Nhẫn")
                                        .gender(null)
                                        .parentCategory(null)
                                        .build();
                        root = categoryRepository.save(root); // Lưu "Nhẫn"
                        Category maleRing = Category.builder()
                                        .categoryName("Nhẫn Nam")
                                        .gender(Gender.MALE)
                                        .parentCategory(root)
                                        .build();

                        Category femaleRing = Category.builder()
                                        .categoryName("Nhẫn Nữ")
                                        .gender(Gender.FEMALE)
                                        .parentCategory(root)
                                        .build();

                        Category proposalRing = Category.builder()
                                        .categoryName("Nhẫn Cầu Hôn")
                                        .gender(null)
                                        .parentCategory(root)
                                        .build();

                        Category weddingRing = Category.builder()
                                        .categoryName("Nhẫn Cưới")
                                        .gender(null)
                                        .parentCategory(root)
                                        .build();
                        Category jewelrySet = Category.builder()
                                        .categoryName("Bộ Trang Sức")
                                        .gender(null)
                                        .parentCategory(root)
                                        .build();

                        // Lưu các category con
                        categoryRepository.saveAll(List.of(maleRing, femaleRing, proposalRing, weddingRing,jewelrySet));

                        System.out.println("Database initialized successfully with categories!");
                } else {
                        System.out.println("Categories already exist in the database. Skipping initialization.");
                }
        }
}
