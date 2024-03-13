package org.main.DIDsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RouterController {
    @GetMapping({"/login","/","logout"})
    public String toLogin() {
        return "login";
    }
    @RequestMapping({"/welcome"})
    public String toWelcome() {
        return "welcome";
    }

    @GetMapping({"/register"})
    public String toRegister() {
        return "register";
    }

}
