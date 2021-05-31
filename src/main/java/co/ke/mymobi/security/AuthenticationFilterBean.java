package co.ke.mymobi.security;

import co.ke.mymobi.utils.Utilities;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class AuthenticationFilterBean extends GenericFilterBean {
    private static final Logger log = LoggerFactory.getLogger(AuthenticationFilterBean.class);
    private static final String HEADER_STRING = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";
//    private static final String PUBLIC_KEY_FILENAME = SB_FILE_PATH + "public_key.der";
    // private static final String PUBLIC_KEY_FILENAME = SB_FILE_PATH+"public_key.der";


    @Value("${files}")
    private String filepath;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = ((HttpServletRequest) request);
        String token = httpRequest.getHeader(HEADER_STRING);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (token != null) {
            try {
                Claims claims = Jwts.parser()
                        .setSigningKey(Utilities.getPublicKey(filepath + "/public.der"))
                        .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                        .getBody();

                AccountModel userModel = new ObjectMapper().convertValue(claims.get("user"), AccountModel.class);
                String username = (String) claims.get("username");
                if (userModel != null) {
                    List<GrantedAuthority> authorities = new ArrayList<>();
                    if (userModel.getAccount() != null) {
                        for (PermissionModel permission : userModel.getAccount().getPermissions()) {
                            authorities.add(new SimpleGrantedAuthority(permission.getName()));
                        }
                    }
                    authentication = new UsernamePasswordAuthenticationToken(new ApiPrincipal(userModel,username), null, authorities);
                }
            } catch (JwtException e) {
                authentication = null;
            }
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }
}
