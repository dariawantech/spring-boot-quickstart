package com.dariawan.meetdash.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PingController {

    @GetMapping("/ping")
    @ResponseBody
    public String pong() {
        return "pong :: Dariawan's Spring Boot";
    }
}
