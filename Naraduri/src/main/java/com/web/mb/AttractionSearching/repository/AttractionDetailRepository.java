package com.web.mb.AttractionSearching.repository;

import com.web.mb.AttractionSearching.model.Attraction_Detail;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface AttractionDetailRepository extends JpaRepository<Attraction_Detail, Long> {
    Optional<Attraction_Detail> findBySite(String site);
    Optional<Attraction_Detail> findByNo(Long no);
}