package co.ke.auth.models;

import co.ke.auth.entities.Role;
import co.ke.auth.entities.Permission;
import co.ke.auth.entities.Tenant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleModel {
    private Integer id;
    private String name;
    private Tenant tenant;
    private Collection<Permission> permissions;


    public static RoleModel transform(Role role, Collection<Permission> permissions){
        RoleModel model = new RoleModel();
        model.setId(role.getId());
        model.setName(role.getName());
        model.setTenant(role.getTenant());
        model.setPermissions(permissions);
        return model;
    }
}
