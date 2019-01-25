package com.plml.pplatform.mainPage;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pplatform")
public class MainController {

    @GetMapping("/")
    public String index() {
        return "Witaj w platformie!";
    }

    @GetMapping("/adminMessage")
    @Secured("ROLE_ADMIN")
    public String adminIndex() {
        return "Widzę, że kolega jest ADMINem!!!";
    }
}
