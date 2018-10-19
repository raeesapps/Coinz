package net.raeesaamir.coinz.messaging.simple;

import net.raeesaamir.coinz.authentication.simple.SimpleUser;
import net.raeesaamir.coinz.messaging.Message;

public class SimpleMessage implements Message<SimpleUser> {

    private final String message;
    private final long createdAt;
    private final SimpleUser sender;

    public SimpleMessage(String message, long createdAt, SimpleUser sender) {
        this.message = message;
        this.createdAt = createdAt;
        this.sender = sender;
    }

    @Override
    public long createdAt() {
        return createdAt;
    }

    @Override
    public String message() {
        return message;
    }

    @Override
    public SimpleUser sender() {
        return sender;
    }
}
