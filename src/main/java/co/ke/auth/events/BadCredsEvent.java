package co.ke.auth.events;

import co.ke.auth.entities.User;
import org.springframework.context.ApplicationEvent;

public class BadCredsEvent extends ApplicationEvent {
    private User user;

    public BadCredsEvent(Object source, User user) {
        super(source);
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
