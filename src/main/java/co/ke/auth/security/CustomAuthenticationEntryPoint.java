package co.ke.auth.security;


import co.ke.auth.utils.Status;
import co.ke.auth.utils.Utilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public final class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    public static final Logger log = LoggerFactory.getLogger(CustomAuthenticationEntryPoint.class);

    public CustomAuthenticationEntryPoint() {
    }

    @Override
    public void commence(final HttpServletRequest request, final HttpServletResponse response, final AuthenticationException authException) throws IOException {
//            log.warn("User: FAILED {}", authException);

        if (authException instanceof BadCredentialsException) {
            //invalid login, update to user_attempts
//            Status status = userService.updateAttempts(authentication.getName(), 1, Util.getIP());
//            String error = status.getMessage();
//            if (status.getCode() == Response.ACCOUNT_BLOCKED.status().getCode()) {
//                // locked user, throw exception
//                throw new LockedException(error);
//            } else {
//                throw new BadCredentialsException(error);
//            }
        } else if (authException instanceof DisabledException) {
            response.setHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE);

            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getOutputStream().println(Utilities.toJson(new Status(403, "Account is blocked!")));
        }else if ( authException instanceof LockedException) {
            response.setHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE);

            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getOutputStream().println(Utilities.toJson(new Status(403, "User is not enabled!")));
        }
//        else if (authException instanceof InsufficientAuthenticationException) {
//            response.setHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE);
//
//            response.setStatus(HttpStatus.UNAUTHORIZED.value());
//            response.getOutputStream().println(Utilities.toJson(new Status(401, "Invalid login credentials!")));
//        }
        else {
//            throw new BadCredentialsException("error      ");


            response.setHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE);

            response.setStatus(HttpStatus.UNAUTHORIZED.value());

            response.getOutputStream().println(Utilities.toJson(new Status(403, authException.getMessage())));
        }
    }
}

