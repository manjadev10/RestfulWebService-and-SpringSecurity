package net.mahamanjari.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String homepage(){
        return "welcome";
    }

    @GetMapping("/login")
    public String customLoginPage(){
        return "login";
    }

    @GetMapping("/welcome")
    public String welcomePage(){
        return "welcome";
    }

    @GetMapping("/authenticated")
    public String authenticatedPage(){
        return "authenticated";
    }

    @GetMapping("/logout")
    public String logoutPage(){
        return "redirect:/welcome";
    }
}
