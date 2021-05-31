package co.ke.mymobi.security;


import co.ke.mymobi.entities.Account;
import co.ke.mymobi.entities.Permission;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class AccountsModel {

    private Integer id;
    private String username;
    private boolean blocked;
    private Date dateBlocked;
    private List<PermissionModel> permissions;

    public static AccountsModel transform(Account account,List<Permission> acPermissions) {
        AccountsModel accountsModel = new AccountsModel();
        accountsModel.setId(account.getId());

        if (account.isBlocked()) {
            accountsModel.setBlocked(true);
            accountsModel.setDateBlocked(account.getDateBlocked());
        } else {
            accountsModel.setBlocked(false);
            accountsModel.setDateBlocked(null);
        }
        accountsModel.setUsername(account.getUsername());
        List<PermissionModel> permissions = new ArrayList<>();
        if (!acPermissions.isEmpty()) {
            for (Permission userPermission : acPermissions) {
                permissions.add(PermissionModel.transform(userPermission));
            }
        }
        accountsModel.setPermissions(permissions);
        return accountsModel;
    }

    public static AccountsModel transformPermissions(Account account) {
        AccountsModel accountsModel = new AccountsModel();
        accountsModel.setId(account.getId());

        if (account.isBlocked()) {
            accountsModel.setBlocked(true);
            accountsModel.setDateBlocked(account.getDateBlocked());
        } else {
            accountsModel.setBlocked(false);
            accountsModel.setDateBlocked(null);
        }
        accountsModel.setUsername(account.getUsername());
        List<PermissionModel> permissions = new ArrayList<>();
        if (account.getPermissions() != null) {
            for (Permission userPermission : account.getPermissions()) {
                permissions.add(PermissionModel.transform(userPermission));
            }
        }
        accountsModel.setPermissions(permissions);
        return accountsModel;
    }
}
