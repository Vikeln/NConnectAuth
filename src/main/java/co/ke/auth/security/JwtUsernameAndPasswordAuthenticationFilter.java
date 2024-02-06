package co.ke.auth.security;

import co.ke.auth.entities.Account;
import co.ke.auth.entities.User;
import co.ke.auth.repositories.AccountDao;
import co.ke.auth.repositories.PermissionDao;
import co.ke.auth.repositories.UserDao;
import co.ke.auth.utils.Utilities;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.*;


public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AccountDao accountDao;

    private PermissionDao permissionDao;

    private UserDao userDao;

    public static final Logger log = LoggerFactory.getLogger(CustomAuthenticationEntryPoint.class);
    private final JwtConfig jwtConfig;
    // We use auth manager to validate the user credentials
    private AuthenticationManager authManager;


    public JwtUsernameAndPasswordAuthenticationFilter(AuthenticationManager authManager, JwtConfig jwtConfig, AccountDao accountDao) {
        this.authManager = authManager;
        this.jwtConfig = jwtConfig;
        this.accountDao = accountDao;

        // By default, UsernamePasswordAuthenticationFilter listens to "/login" path.
        // In our case, we use "/auth". So, we need to override the defaults.
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(jwtConfig.getUri(), "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            // 1. Get credentials from request
            UserCredentials creds = new ObjectMapper().readValue(request.getInputStream(), UserCredentials.class);
            // 2. Create auth object (contains credentials) which will be used by auth manager
            if (creds.getPassword() == null) {
                Account account;
                Optional<Account> account1 = accountDao.findTop1ByUsername(creds.getUsername());
//                if (account1.isPresent()) {
                account = account1.get();
//                    log.info("ACCOUNT {}", Utilities.toJson(account));
//                } else {
//                    log.info("Couldnt find ACCOUNT ");
//                    throw new InsufficientAuthenticationException();
//                }
                AccountModel accountModel;
                User user = account.getUserId();
                List<Integer> acPermissions = accountDao.findAccountPermissions(account.getId());
                accountModel = AccountModel.convertUser(user, AccountsModel.transform(account, permissionDao.findAllByIdIn(acPermissions)), account);
                UserPrincipal principal = new UserPrincipal(account, accountModel);
                return new UsernamePasswordAuthenticationToken(principal, null, new ArrayList<>());
            } else {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        creds.getUsername(), creds.getPassword(), Collections.emptyList());
                return authManager.authenticate(authToken);
            }
            // 3. Authentication manager authenticate the user, and use UserDetialsServiceImpl::loadUserByUsername() method to load the user.
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Upon successful authentication, generate a token.
    // The 'auth' passed to successfulAuthentication() is the current authenticated user.
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication auth) throws IOException {

        Long now = System.currentTimeMillis();
        Date exp = new Date(now + jwtConfig.getExpiration() * 1000);

        AccountModel principal = ((UserPrincipal) auth.getPrincipal()).getUser();

        log.error("SUCCESS AUTH: {}", principal.getAccount().getUsername());

        HashMap<String, Object> data = new HashMap<>();
        data.put("user", principal);
        data.put("username", principal.getAccount().getUsername());
        String token = Jwts.builder()
                .setSubject(auth.getName())
                // Convert to list of strings.
                // This is important because it affects the way we get them back in the Gateway.
                .setClaims(data)
                .setIssuedAt(new Date(now))
                .setExpiration(exp)  // in milliseconds
                .signWith(SignatureAlgorithm.RS512, Utilities.getPrivateKey(jwtConfig.getFilePath() + "private.der"))
                .compact();

        TokenResponse tokenResponse = new TokenResponse(token, exp);
        response.setHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        response.getOutputStream().println(Utilities.toJson(tokenResponse));
    }

    // A (temporary) class just to represent the user credentials
    private static class UserCredentials {
        private String username, password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
