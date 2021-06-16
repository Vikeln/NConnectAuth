package co.ke.auth.events;

import co.ke.auth.entities.User;
import org.springframework.context.ApplicationEvent;

public class NewUserCreatedEvent extends ApplicationEvent {
    private User user;
    private String password;
    private String tenantName;
    private String tenantAdminPanel;

    public NewUserCreatedEvent(Object source, String password, User user, String tenantName, String tenantAdminPanel) {
        super(source);
        this.user = user;
        this.password = password;
        this.tenantName = tenantName;
        if (tenantAdminPanel != null)
            this.tenantAdminPanel = tenantAdminPanel;
    }

    public String getTenantAdminPanel() {
        return tenantAdminPanel;
    }

    public void setTenantAdminPanel(String tenantAdminPanel) {
        this.tenantAdminPanel = tenantAdminPanel;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
