package de.darfichraus.controller;

import de.darfichraus.entity.Credentials;
import de.darfichraus.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class UserController {
    final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/login",
            consumes = {"application/json"},
            method = RequestMethod.POST)
    public ResponseEntity<String> authenticateUser(@Valid @RequestBody Credentials credentials) {
        return ResponseEntity.ok(userService.authenticateUser(credentials));
    }
}
