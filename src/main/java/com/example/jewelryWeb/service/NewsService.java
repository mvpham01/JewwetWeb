package com.example.jewelryWeb.service;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.jewelryWeb.models.Entity.News;
import com.example.jewelryWeb.repository.NewsRepository;
@Service
public class NewsService {
@Autowired
    private NewsRepository newsRepository;



    // Lấy tất cả các tin tức
    public List<News> getAllNews() {
        return newsRepository.findAll();
    }

    // Lấy tin tức theo ID
    public Optional<News> getNewsById(Long newsId) {
        return newsRepository.findById(newsId);
    }

    // Thêm tin tức mới
    public News addNews(News news) {
        return newsRepository.save(news);
    }

    // Cập nhật tin tức
    public News updateNews(Long newsId, News updatedNews) {
        return newsRepository.findById(newsId).map(news -> {
            news.setTitle(updatedNews.getTitle());
            news.setContent(updatedNews.getContent());
            news.setImage(updatedNews.getImage());
            news.setPublishedAt(updatedNews.getPublishedAt());
            news.setIsActive(updatedNews.getIsActive());
            return newsRepository.save(news);
        }).orElseThrow(() -> new IllegalArgumentException("News not found with id: " + newsId));
    }

    // Xóa tin tức
    public void deleteNews(Long newsId) {
        newsRepository.deleteById(newsId);
    }
}