package com.example.paginationandprojection.web.controller;

import com.example.paginationandprojection.utils.Pages;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public String displayHomePage(){
        return Pages.HOME;
    }
}
