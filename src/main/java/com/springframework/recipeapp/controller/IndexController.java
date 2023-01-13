package com.springframework.recipeapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping({"","/"})
    public String getIndexPage() {
//        System.out.println("say something.......");
        return "index";
    }
}
