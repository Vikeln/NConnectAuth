package co.ke.auth.events;

import co.ke.auth.entities.Role;
import org.springframework.context.ApplicationEvent;

public class RoleChangeEvent extends ApplicationEvent {
    private String realm;
    private Role role;

    public RoleChangeEvent(Object source, String realm, Role role) {
        super(source);
        this.realm = realm;
        this.role = role;
    }


    public String getRealm() {
        return realm;
    }

    public void setRealm(String realm) {
        this.realm = realm;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
