package com.project.resume.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@SuppressWarnings("SameReturnValue")
@Controller
public class AboutController {
    @GetMapping
    public String index(Model model) {
        return "about/about_page";
    }
}