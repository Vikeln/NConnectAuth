package co.ke.mymobi.security;


import co.ke.mymobi.services.UserService;
import co.ke.mymobi.utils.Response;
import co.ke.mymobi.utils.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import javax.servlet.http.HttpServletRequest;

public class LimitLoginAuthenticationProvider extends DaoAuthenticationProvider {
    public static final Logger log = LoggerFactory.getLogger(CustomAuthenticationEntryPoint.class);
    @Autowired
    private UserService userService;

    @Autowired
    private HttpServletRequest request;

    @Override
    public Authentication authenticate(Authentication authentication) {
        try {
            Authentication auth = super.authenticate(authentication);
            //if reach here, means login success, else an exception will be thrown
            //reset the user_attempts
            userService.updateAttempts(authentication.getName(), 0);
            return auth;
        } catch (AuthenticationException ex) {
            log.error("ERROR_1: " + ex.getMessage());
            if (ex instanceof BadCredentialsException) {
                //invalid login, update to user_attempts
                Status status = userService.updateAttempts(authentication.getName(), 1);
                String error = status.getMessage();
                if (status.getCode() == Response.ACCOUNT_BLOCKED.status().getCode() || status.getCode() == Response.ACCOUNT_NOT_ENABLED.status().getCode()) {
                    // locked user, throw exception
                    throw new DisabledException(error);
                } else {
                    throw new BadCredentialsException(error);
                }
            } else if (ex instanceof LockedException) {
                throw new DisabledException(Response.ACCOUNT_BLOCKED.status().getMessage());
            } else if (ex instanceof DisabledException) {
                throw new DisabledException(Response.ACCOUNT_BLOCKED.status().getMessage());
            } else {
                throw new BadCredentialsException("error");
            }
        }
    }
}