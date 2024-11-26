package com.example.jewelryWeb.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.jewelryWeb.models.Entity.Jemmia;
@Repository
public interface JemmiaRepository extends JpaRepository<Jemmia, Long> {
    boolean existsByTitle(String title);
}