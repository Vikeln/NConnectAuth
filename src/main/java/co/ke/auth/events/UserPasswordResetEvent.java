package co.ke.auth.events;


import co.ke.auth.entities.User;

public class UserPasswordResetEvent {
    private User user;
    private String tenantName;
    private String tenantAdminPanel;

    public UserPasswordResetEvent(User user, String tenantName, String tenantAdminPanel) {
        this.user = user;
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

    public User getUser() {
        return user;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
