package com.example.jewelryWeb.models.Entity;

import lombok.*;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;


@Entity
@Table(name = "category")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @Column(nullable = false)
    private String categoryName;

    @Column(length = 10)
    @Enumerated(EnumType.STRING)
    private Gender gender;

     @ManyToOne
    @JsonIgnore // Ngăn chặn tuần tự hóa đệ quy
    private Category parentCategory;

    @OneToMany(mappedBy = "parentCategory", cascade = CascadeType.ALL)
    private List<Category> subCategories;

    @OneToMany(mappedBy = "category")
    @JsonIgnore // Ngăn không cho tuần tự hóa danh sách sản phẩm
    private Set<Product> products;
    
}