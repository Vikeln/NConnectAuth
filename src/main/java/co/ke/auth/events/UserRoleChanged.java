package co.ke.auth.events;

import co.ke.auth.entities.User;
import org.springframework.context.ApplicationEvent;


public class UserRoleChanged extends ApplicationEvent {
    private User user;
    private Integer roleId;

    public UserRoleChanged(Object source,User user, Integer roleId) {
        super(source);
        this.user=user;
        this.roleId=roleId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}
