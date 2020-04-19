package org.amsidh.mvc.controller;

import lombok.extern.slf4j.Slf4j;
import org.amsidh.mvc.service.WelcomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class WelcomeController {
    @Autowired
    private WelcomeService welcomeService;

    public WelcomeController() {
        log.info("WelcomeController Loading!!!");
    }

    @GetMapping(value = {"/", "/welcome"})
    public String welcome() {
        return welcomeService.getWelcomeMessage();
    }
}
