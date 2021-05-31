package co.ke.mymobi.security;


import co.ke.mymobi.entities.Account;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class UserPrincipal implements UserDetails {
    private boolean isBlocked;
    private String username;
    private String password;
    private AccountModel account;

    public UserPrincipal(Account user, AccountModel account) {
        this.account = account;
        this.username = user.getUsername();
        this.isBlocked = user.isBlocked();
        this.password = user.getPassword();
    }

    public AccountModel getUser() {
        return account;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (PermissionModel permission : account.getAccount().getPermissions()) {
            authorities.add(new SimpleGrantedAuthority(permission.getName()));
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return !isBlocked;
    }
}