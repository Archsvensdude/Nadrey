package com.web.mb.AttractionSearching.repository;

import com.web.mb.AttractionSearching.model.Attraction_Theme;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttractionThemeRepository extends JpaRepository<Attraction_Theme, Long> {
    Page<Attraction_Theme> findByTheme(String category, Pageable pageable);
}