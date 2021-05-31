package co.ke.mymobi.models;


import co.ke.mymobi.entities.Permission;
import co.ke.mymobi.entities.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author TMwaura on 28/10/2019
 * @Project admin-dashboard
 */
public class RoleDto {

    private Integer id;

    @NotNull(message = "Name cannot be Empty")
    private String name;

    private List<String> permissions;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public RoleDto() {
    }

    public RoleDto(String name) {
        this.name = name;
    }

    @JsonIgnore
    public Role getRole() {
        Role role = new Role();
        role.setName(name);
        return role;
    }

    public static RoleDto transform(Role role, List<Permission> permissions) {
        RoleDto model = new RoleDto();
        model.setId(role.getId());
        model.setName(role.getName());
        model.setPermissions(permissions.stream().map(r -> r.getName()).collect(Collectors.toList()));
        return model;
    }

    public static RoleDto transform(String role, List<Permission> permissions) {
        RoleDto model = new RoleDto();
        model.setName(role);
        model.setPermissions(permissions.stream().map(r -> r.getName()).collect(Collectors.toList()));
        return model;
    }
}
