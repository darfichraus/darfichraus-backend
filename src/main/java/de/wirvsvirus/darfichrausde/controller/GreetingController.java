package de.wirvsvirus.darfichrausde.controller;

import de.wirvsvirus.darfichrausde.dto.Greeting;
import de.wirvsvirus.darfichrausde.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    private TestService service;

    @Autowired
    public GreetingController(TestService service) {
        this.service = service;
    }

    @GetMapping("/greeting/{name}")
    public Greeting greeting(@PathVariable String name) {
        return service.greet(name);
    }
}
