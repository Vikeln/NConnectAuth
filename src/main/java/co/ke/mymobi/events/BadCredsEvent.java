package co.ke.mymobi.events;

import co.ke.mymobi.entities.User;
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
