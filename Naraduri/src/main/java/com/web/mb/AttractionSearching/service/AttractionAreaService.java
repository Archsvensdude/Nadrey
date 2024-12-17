package com.web.mb.AttractionSearching.service;

import com.web.mb.AttractionSearching.model.Attraction_Area;
import com.web.mb.AttractionSearching.repository.AttractionAreaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AttractionAreaService {

    @Autowired
    private AttractionAreaRepository attractionAreaRepository;

    public Page<Attraction_Area> getAttractionsByRegion(int page, String region) {
        int pageSize = 10; // 페이지당 항목 수
        Pageable pageable = PageRequest.of(page, pageSize);

        if ("ALL".equalsIgnoreCase(region)) {
            return attractionAreaRepository.findAll(pageable);
        } else {
            return attractionAreaRepository.findByRegion(region, pageable);
        }
    }
}

