package co.ke.auth.models;

import co.ke.auth.entities.Tenant;
import co.ke.auth.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TenantModel {

    private Integer id;

    private boolean status;

    private String name;

    private String appKey;

    private User adminUser;

    public static TenantModel transform(Tenant tenant, User user) {
        TenantModel model = new TenantModel();
        model.setAdminUser(user);
        model.setAppKey(tenant.getAppKey());
        model.setId(tenant.getId());
        model.setStatus(tenant.isStatus());
        model.setName(tenant.getName());
        return model;
    }
}
