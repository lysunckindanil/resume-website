package com.project.resume.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@SuppressWarnings("SameReturnValue")
@Controller
public class ResourceController {
    @GetMapping("/resources")
    private String resources() {
        return "resources/resources_page";
    }
}
