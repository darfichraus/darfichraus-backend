package de.wirvsvirus.darfichrausde.service;

import de.wirvsvirus.darfichrausde.dto.Greeting;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    public Greeting greet(final String name) {
        return new Greeting(name);
    }
}
