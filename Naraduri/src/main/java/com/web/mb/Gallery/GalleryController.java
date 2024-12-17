package com.web.mb.Gallery;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/gallery")
public class GalleryController {

    @GetMapping("")
    public String photoGallery() {
        return "th/gallery";
    }

    /* @GetMapping("/review")
    public String ReviewBoard() {
        return "th/review";
    } */

}
