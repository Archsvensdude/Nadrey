package com.web.mb;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/")
public class IndexController {
    /*@Autowired
    private EmpDAO empDAO;*/

    @GetMapping("")
    public String index() {
        return "th/index";
    }
}