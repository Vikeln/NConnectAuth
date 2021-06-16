package co.ke.auth.utils;

import co.ke.auth.security.ApiPrincipal;
import co.ke.auth.security.AccountModel;
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
        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal() instanceof String) {
            return Optional.of("Application Root");
        }

        AccountModel principal = ((ApiPrincipal) authentication.getPrincipal()).getUser();
        String currentPrincipalName = principal.getAccount().getUsername();
        return Optional.of(currentPrincipalName);
    }

}
