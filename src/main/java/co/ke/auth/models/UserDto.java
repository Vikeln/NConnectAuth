package co.ke.auth.models;

import co.ke.auth.annotations.PhoneNumberExists;
import co.ke.auth.entities.User;
import co.ke.auth.annotations.EmailExists;
import co.ke.auth.annotations.UserName;
import co.ke.auth.entities.Permission;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@ToString
public class UserDto {
    private Integer id;

    @NotNull(message = "Username Cannot be null")
    @Size(min = 2, message = "Username Size must be > 2 ")
    @UserName
    private String userName;

    private String tenantKey;

    public String getTenantKey() {
        return tenantKey;
    }

    public void setTenantKey(String tenantKey) {
        this.tenantKey = tenantKey;
    }

    @NotNull(message = "First Name Cannot be null")
    @Size(min = 2, message = "First Name Size must be > 2 ")
    private String firstName;

    @NotNull(message = "Last Name Cannot be null")
    @Size(min = 2, message = "Last Name Size must be > 2 ")
    private String lastName;

    @Size(min = 2, message = "Other Name Size must be > 2 ")
    private String otherName;

    @Email
    @NotNull(message = "Email cannot be null")
    @Size(min = 1, message = "Email Size must be > 1")
    @EmailExists
    private String email;

    private Boolean enabled = false;

    @PhoneNumberExists
    private String phoneNumber;

    @NotNull(message = "Role cannot be Empty")
    private Integer role;

    @NotNull(message = "userPermissions cannot be Empty")
    private List<String> userPermissions = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<String> getUserPermissions() {
        return userPermissions;
    }

    public void setUserPermissions(List<String> userGroups) {
        this.userPermissions = userGroups;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public String getOtherName() {
        return otherName;
    }

    public void setOtherName(final String lastName) {
        this.otherName = lastName;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @JsonIgnore
    public User getUser() {
        User user = new User();
        user.setEnabled(false);
        user.setUserName(this.userName);
        user.setFirstName(this.firstName);
        user.setLastName(this.lastName);
        user.setEmail(this.email);
        user.setEnabled(this.enabled);
        user.setPhoneNumber(this.phoneNumber);
        return user;
    }

    public static UserDto transform(User user, List<String> userPermissions) {
        UserDto model = new UserDto();
        model.setEmail(user.getEmail());
        model.setEnabled(user.isEnabled());
        model.setFirstName(user.getFirstName());
        model.setId(user.getId());
        model.setTenantKey(user.getTenant().getAppKey());
        model.setLastName(user.getLastName());
        model.setOtherName(user.getOtherName());
        model.setPhoneNumber(user.getPhoneNumber());
        model.setRole(user.getRole().getId());
        model.setUserName(user.getUserName());
        if (!userPermissions.isEmpty())
            model.setUserPermissions(userPermissions);
        return model;
    }


    public static UserDto transform(TenantDTo customerDto, Integer role, Collection<Permission> permissions) {
        UserDto model = new UserDto();
        model.setUserName(customerDto.getAdminUserName());
        model.setFirstName(customerDto.getAdminFirstName());
        model.setLastName(customerDto.getAdminLastName());
        model.setOtherName(customerDto.getAdminOtherName());
        model.setEmail(customerDto.getAdminEmail());
        model.setEnabled(false);
        model.setPhoneNumber(customerDto.getAdminPhoneNumber());
        model.setRole(role);
        model.setUserPermissions(permissions.stream().map(permission -> permission.getName()).collect(Collectors.toList()));
        return model;
    }


    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder
                .append("UserDto [firstName=").append(firstName)
                .append(", lastName=").append(lastName)
                .append(", email=").append(email)
                .append(", role=").append(role).
                append("]");
        return builder.toString();
    }

}