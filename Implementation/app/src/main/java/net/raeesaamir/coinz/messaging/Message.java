package net.raeesaamir.coinz.messaging;

import net.raeesaamir.coinz.authentication.User;

/**
 * An interface for message implementation.
 * @param <U> The user object
 */
public interface Message<U extends User> {

    /**
     * The message contents
     * @return a string containing the message contents
     */
    String message();

    /**
     * The user object
     * @return The user object
     */
    U sender();

    /**
     * The date at which the message was created at.
     * @return A long representing creation date
     */
    long createdAt();
}
