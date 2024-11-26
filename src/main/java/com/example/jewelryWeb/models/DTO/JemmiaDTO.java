package com.example.jewelryWeb.models.DTO;
import java.time.LocalDateTime;

import org.springframework.web.multipart.MultipartFile;

import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JemmiaDTO {
    private String title;
    private String contentHeader;
    private MultipartFile image; // Image file
    private String contentFooter;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime publishedAt;
    private Boolean isActive;
}
