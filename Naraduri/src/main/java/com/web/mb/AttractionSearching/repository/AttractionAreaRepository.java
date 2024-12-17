package com.web.mb.AttractionSearching.repository;

import com.web.mb.AttractionSearching.model.Attraction_Area;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttractionAreaRepository extends JpaRepository<Attraction_Area, Long> {
    Page<Attraction_Area> findByRegion(String region, Pageable pageable);
}