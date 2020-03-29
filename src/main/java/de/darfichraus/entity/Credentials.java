package de.darfichraus.entity;

import org.pac4j.core.credentials.UsernamePasswordCredentials;

public class Credentials extends UsernamePasswordCredentials {
    public Credentials() {
        super(null, null);
    }

    public Credentials(String username, String password) {
        super(username, password);
    }
}
