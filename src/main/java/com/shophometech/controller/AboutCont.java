package com.shophometech.controller;

import com.shophometech.controller.main.Attributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/about")
public class AboutCont extends Attributes {

    @GetMapping
    public String About(Model model) {
        addAttributes(model);
        return "about";
    }

}
