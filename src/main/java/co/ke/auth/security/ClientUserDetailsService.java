package co.ke.auth.security;


import co.ke.auth.entities.Account;
import co.ke.auth.entities.User;
import co.ke.auth.repositories.AccountDao;
import co.ke.auth.repositories.PermissionDao;
import co.ke.auth.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientUserDetailsService implements UserDetailsService {
    @Autowired
    private AccountDao accountDao;
    @Autowired
    private PermissionDao permissionDao;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Account account = accountDao.findTop1ByUsername(username).get();
        if (account != null) {
            if (account.isBlocked())
                throw new LockedException(Response.ACCOUNT_BLOCKED.status().getMessage());
            User user = account.getUserId();
            if (!user.isEnabled())
                throw new LockedException(Response.ACCOUNT_NOT_ENABLED.status().getMessage());
            List<Integer> acPermissions = accountDao.findAccountPermissions(account.getId());

            return new UserPrincipal(account, AccountModel.convertUser(user, AccountsModel.transform(account, permissionDao.findAllByIdIn(acPermissions)), account));
        }
        throw new UsernameNotFoundException(Response.USER_NOT_FOUND.status().getMessage());
    }
}