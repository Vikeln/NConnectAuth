package co.ke.mymobi.models;


import co.ke.mymobi.entities.Tenant;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotNull;

public class TenantDTo {
    private Integer id;
    @NotNull(message = "Tenant Name cannot be Empty")
    private String name;
    private String clientBaseUrl;

    private boolean createAdminUser = false;
    private String adminFirstName;
    private String adminLastName;
    private String adminOtherName;
    private String adminUserName;
    private String adminEmail;
    private String adminPhoneNumber;


    public String getClientBaseUrl() {
        return clientBaseUrl;
    }

    public void setClientBaseUrl(String clientBaseUrl) {
        this.clientBaseUrl = clientBaseUrl;
    }


    public String getAdminFirstName() {
        return adminFirstName;
    }

    public void setAdminFirstName(String adminFirstName) {
        this.adminFirstName = adminFirstName;
    }

    public String getAdminLastName() {
        return adminLastName;
    }

    public void setAdminLastName(String adminLastName) {
        this.adminLastName = adminLastName;
    }

    public String getAdminOtherName() {
        return adminOtherName;
    }

    public void setAdminOtherName(String adminOtherName) {
        this.adminOtherName = adminOtherName;
    }

    public String getAdminUserName() {
        return adminUserName;
    }

    public void setAdminUserName(String adminUserName) {
        this.adminUserName = adminUserName;
    }

    public String getAdminEmail() {
        return adminEmail;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    public String getAdminPhoneNumber() {
        return adminPhoneNumber;
    }

    public void setAdminPhoneNumber(String adminPhoneNumber) {
        this.adminPhoneNumber = adminPhoneNumber;
    }

    public boolean isCreateAdminUser() {
        return createAdminUser;
    }

    public void setCreateAdminUser(boolean createAdminUser) {
        this.createAdminUser = createAdminUser;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    public Tenant getTenant() {
        Tenant tenant = new Tenant();
        if (id != null)
            tenant.setId(id);
        if (clientBaseUrl != null)
            tenant.setClientBaseUrl(clientBaseUrl);
        tenant.setStatus(true);
        tenant.setName(name);
        return tenant;
    }
}
