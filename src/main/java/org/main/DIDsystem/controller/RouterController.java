package org.main.DIDsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RouterController {
    @GetMapping({"/","logout"})
    public String toLogin() {
        return "index";
    }
    @GetMapping({"/welcome"})
    public String toWelcome() {
        return "welcome";
    }

}
