package org.main.DIDsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TransactionController {
    @RequestMapping("/")
    public String index(){

        return "index";
    }
}