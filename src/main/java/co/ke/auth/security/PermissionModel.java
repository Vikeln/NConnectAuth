package co.ke.auth.security;

import co.ke.auth.entities.Permission;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class PermissionModel {
    private String name;

    public static PermissionModel transform(Permission permission) {
        PermissionModel model = new PermissionModel();
        model.setName(permission.getName());
        return model;
    }
}
