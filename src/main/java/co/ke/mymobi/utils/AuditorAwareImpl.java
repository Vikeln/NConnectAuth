package co.ke.mymobi.utils;

import co.ke.mymobi.security.AccountModel;
import co.ke.mymobi.security.ApiPrincipal;
import co.ke.mymobi.security.UserPrincipal;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

/**
 * @author TMwaura on 28/10/2019
 * @Project admin-dashboard
 */
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.of("Application Root");
        }
        AccountModel principal = ((ApiPrincipal) authentication.getPrincipal()).getUser();
        String currentPrincipalName = principal.getAccount().getUsername();
        return Optional.of(currentPrincipalName);
    }

}
