package co.ke.auth.security;


import co.ke.auth.entities.Account;
import co.ke.auth.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AccountModel implements Serializable {
    private static final long serialVersionUID = -3622386171997650688L;

    private Item userType;
        private CommonModel accountOwner;
        private String client;
    private AccountsModel account;


    public static AccountModel convertUser(User user, AccountsModel accounts, Account account) {
        return doConvert(user, accounts, account);
    }


    public static AccountModel doConvert(User user, AccountsModel accounts, Account account) {
        AccountModel model = new AccountModel();
        model.setAccountOwner(CommonModel.transform(user));
        model.setUserType(new Item(user.getUserType().getId(), user.getUserType().getName()));
        model.setClient(user.getTenant().getAppKey());
        model.setAccount(accounts);
        return model;
    }


}
