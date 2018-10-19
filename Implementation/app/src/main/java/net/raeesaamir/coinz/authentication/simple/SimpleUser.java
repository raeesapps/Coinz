package net.raeesaamir.coinz.authentication.simple;

import net.raeesaamir.coinz.authentication.User;

public class SimpleUser implements User {

    private String name;

    public SimpleUser(String name) {
        this.name = name;
    }

    @Override
    public String name() {
        return name;
    }
}
