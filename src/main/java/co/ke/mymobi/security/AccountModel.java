package co.ke.mymobi.security;


import co.ke.mymobi.entities.Account;
import co.ke.mymobi.entities.User;
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
    private AccountsModel account;


    public static AccountModel convertUser(User user, AccountsModel accounts, Account account) {
        return doConvert(user, accounts, account);
    }


    public static AccountModel doConvert(User user, AccountsModel accounts, Account account) {
        AccountModel model = new AccountModel();
//        if (customer != null){
//            model.setAccountOwner(CommonModel.transform(customer));
//            model.setUserType(new Item(customer.getUserType().getId(),customer.getUserType().getName()));
//        }
//        else{
        model.setAccountOwner(CommonModel.transform(user));
        model.setUserType(new Item(user.getUserType().getId(), user.getUserType().getName()));
//        }


        model.setAccount(accounts);
        return model;
    }


}
