package com.web.mb.AttractionSearching.service;

import com.web.mb.AttractionSearching.model.Attraction_Area;
import com.web.mb.AttractionSearching.model.Attraction_Theme;
import com.web.mb.AttractionSearching.repository.AttractionThemeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttractionThemeService {

    @Autowired
    private AttractionThemeRepository attractionThemeRepository;

    public Page<Attraction_Theme> getAttractionsByTheme(int page, String category) {
        int pageSize = 10; // 페이지당 항목 수
        Pageable pageable = PageRequest.of(page, pageSize);

        if ("ALL".equalsIgnoreCase(category)) {
            return attractionThemeRepository.findAll(pageable);
        } else {
            return attractionThemeRepository.findByTheme(category, pageable);
        }
    }
}
