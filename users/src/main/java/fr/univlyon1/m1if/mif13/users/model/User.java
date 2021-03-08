package fr.univlyon1.m1if.mif13.users.model;

import javax.naming.AuthenticationException;


public class User {
    private String login, password;
    // Permet d'invalider une connexion même si le token est toujours valide
    private boolean connected;

    public User (){}

    public User(String login, String password) {
        this.login = login;
        this.password = password;
        // Quand un utilisateur est créé, il n'est pas connecté
        this.connected = false;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isConnected() {
        return this.connected;
    }

    public void validatePassword(String password) throws AuthenticationException {
        if(!password.equals(this.password)) {
            throw new AuthenticationException("Erroneous password");
        }
        this.connected = true;
    }

    public void disconnect() {
        this.connected = false;
    }
}

