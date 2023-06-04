package com.shophometech.controller;

import com.shophometech.controller.main.Attributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginCont extends Attributes {

    @GetMapping
    public String login(Model model) {
        addAttributes(model);
        return "login";
    }
}
