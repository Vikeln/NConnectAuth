package co.ke.auth.events;

import co.ke.auth.entities.Permission;
import org.springframework.context.ApplicationEvent;

import java.util.Collection;

public class NewPermissionsEvent extends ApplicationEvent {
    private Collection<Permission> permissions;

    public NewPermissionsEvent(Object source, Collection<Permission> permissions) {
        super(source);
        this.permissions = permissions;
    }

    public Collection<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Collection<Permission> permissions) {
        this.permissions = permissions;
    }
}
