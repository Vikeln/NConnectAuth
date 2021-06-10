package co.ke.mymobi.services.impl;


import co.ke.mymobi.clients.HttpClientBuilder;
import co.ke.mymobi.entities.*;
import co.ke.mymobi.events.NewUserCreatedEvent;
import co.ke.mymobi.events.UserPasswordResetEvent;
import co.ke.mymobi.exceptions.PortalException;
import co.ke.mymobi.models.AppResponseModel;
import co.ke.mymobi.models.ResetPassword;
import co.ke.mymobi.models.UserDto;
import co.ke.mymobi.models.UserModel;
import co.ke.mymobi.repositories.*;
import co.ke.mymobi.services.UserService;
import co.ke.mymobi.utils.Response;
import co.ke.mymobi.utils.Status;
import co.ke.mymobi.utils.Utilities;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private AccountDao accountDao;

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    private HttpClientBuilder client = new HttpClientBuilder<>();
    private ObjectMapper mapper = Utilities.mapper();

    private static final String ERRORLOG = "error=[{}]";
    private String userString = "users";
    private String authPrefix = "Bearer ";

    @Value("${users.defaultPassword}")
    private String defaultPassword;

    private static final String ERRORLOGFORMAT = "error=[{}]";

    @Value("${application.login.max.attempts}")
    private int maxLoginAttempts;

    @Autowired
    private TenantDao tenantDao;

    @Autowired
    private PermissionDao permissionDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    ApplicationEventPublisher publisher;

    @Autowired
    private PasswordEncoder encoder;

    @PersistenceContext
    EntityManager em;

    @Override
    public Status updateAttempts(String username, int attempts) {
        Status status;
        if (username != null) {
            log.info(username);
            Account user = accountDao.findTop1ByUsername(username).get();
            if (user != null) {
                if (user.isBlocked()) {
                    status = Response.ACCOUNT_BLOCKED.status();
                } else {
                    //increase failed by +1 else reset to 0
                    attempts = attempts == 0 ? 0 : user.getFailedAttempts() + 1;
                    user.setFailedAttempts(attempts);
                    if (attempts >= 5) {
                        user.setBlocked(true);
                        status = Response.ACCOUNT_BLOCKED.status();
                    } else {
                        user.setLastLogin(new Date());
                        status = Response.SUCCESS.status();
                    }
                    accountDao.save(user);
                }
            } else {
                status = Response.USER_NOT_FOUND.status();
            }
        } else {
            status = Response.USER_NOT_FOUND.status();
        }
        return status;
    }

    @Override
    public AppResponseModel findAll(String tenantKey) {
        AppResponseModel appResponseModel = new AppResponseModel();

        Object verify = verifyRequestAppKey(tenantKey, tenantDao);
        if (verify instanceof AppResponseModel)
            return (AppResponseModel) verify;
        Tenant tenant1 = (Tenant) verify;
        appResponseModel.setData(userDao.findAllByTenantOrderByIdDesc(tenant1));
        appResponseModel.setStatus(true);
        appResponseModel.setMessage("Successfully fetched users!");
        return appResponseModel;
    }

    @Override
    public AppResponseModel viewOne(String tenant, Integer userId) {
        AppResponseModel appResponseModel = new AppResponseModel();
        Optional<User> optionalUser = userDao.findById(userId);
        if (!optionalUser.isPresent()) {
            appResponseModel.setStatus(false);
            appResponseModel.setMessage("User not found using specified ID");
        } else {
            appResponseModel.setStatus(true);
            appResponseModel.setMessage("User fetched successfully!");
            List<Integer> ids = new ArrayList<>();
            ids.add(userId);
            Optional<Account> account = accountDao.findDistinctByUserId(optionalUser.get());
            if (account.isPresent())
                appResponseModel.setData(UserModel.transform(optionalUser.get(), account.get().getPermissions().stream().map(accountPermission -> accountPermission.getName()).collect(Collectors.toList())));
            else
                appResponseModel.setData(UserModel.transform(optionalUser.get(), new ArrayList<>()));
        }
        return appResponseModel;

    }

    @Override
    public AppResponseModel findOne(String tenant, Integer userId) {
        AppResponseModel appResponseModel = new AppResponseModel();
        Optional<User> optionalUser = userDao.findById(userId);
        if (!optionalUser.isPresent()) {
            appResponseModel.setStatus(false);
            appResponseModel.setMessage("User not found using specified ID");
        } else {
            appResponseModel.setStatus(true);
            appResponseModel.setMessage("User fetched successfully!");
            List<Integer> ids = new ArrayList<>();
            ids.add(userId);
            Optional<Account> optional = accountDao.findDistinctByUserId(optionalUser.get());
            if (!optional.isPresent())
                appResponseModel.setData(UserDto.transform(optionalUser.get(), new ArrayList<>()));
            else
                appResponseModel.setData(UserDto.transform(optionalUser.get(), optional.get().getPermissions().stream().map(permission -> permission.getName()).collect(Collectors.toList())));
        }
        return appResponseModel;
    }


    private boolean userNameExists(String userName) {
        return userDao.existsByUserNameIgnoreCase(userName);
    }

    @Override
    public AppResponseModel unlockUserAccount(Integer userId) {
        AppResponseModel responseModel = new AppResponseModel();
        try {
            Account account = accountDao.findById(userId).get();
            if (account != null) {
                if (!account.isBlocked()) {
                    responseModel.setStatus(false);
                    responseModel.setMessage("Account is not blocked");
                } else {
                    account.setBlocked(false);
                    account.setDateBlocked(null);
                    accountDao.save(account);

                    responseModel.setStatus(true);
                    responseModel.setMessage("Success!");
                }
            } else {
                responseModel.setStatus(false);
                responseModel.setMessage("Could not find account!");
            }
        } catch (Exception e) {
            responseModel.setStatus(false);
            responseModel.setMessage("Account could not be unlocked successfully. Please contact the administrator");
            throw new PortalException(e.getMessage());
        }
        return responseModel;
    }

    @Override
    public AppResponseModel changeUserAccountStatus(Integer userId) {
        AppResponseModel responseModel = new AppResponseModel();
        try {
            Optional<User> optionalUser = userDao.findById(userId);
            if (optionalUser.isPresent()) {
                optionalUser.get().setEnabled(false);
                User is = userDao.save(optionalUser.get());
                responseModel.setStatus(true);
                responseModel.setMessage("Account activated successfully");
                responseModel.setData(is);
            } else {
                responseModel.setStatus(false);
                responseModel.setMessage("Account could not be found");
            }
        } catch (Exception e) {
            responseModel.setStatus(false);
            responseModel.setMessage("Account could not be activated successfully. Please contact the administrator");
            throw new PortalException(e.getMessage());
        }
        return responseModel;
    }


    @Override
    public Optional<User> findUserByEmail(String email) {
        try {
            Optional<User> user = userDao.findByEmail(email);
            return user;
        } catch (EntityNotFoundException | NoResultException | NonUniqueResultException ex) {
            log.error(ERRORLOGFORMAT, ex.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public Boolean enabledUser(String userName) {
//        log.info("finding user {}", userName);
        try {
            User user = userDao.findTopByUserNameIgnoreCase(userName);
            return user.isEnabled();
        } catch (EntityNotFoundException | NoResultException | NonUniqueResultException ex) {
            log.error(ERRORLOGFORMAT, ex.getMessage());
        }
        return false;
    }

    @Override
    public String createUpdateUsers() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    public Optional<User> findByUserNameOrEmail(String email, String userName) {
        return userDao.findByEmailAndUserName(email, userName);
    }


    @Override
    public AppResponseModel createUpdateUsers(String tenantKey, UserDto userDto, Integer userType) {

        AppResponseModel appResponseModel = new AppResponseModel();
        Object verify = verifyRequestAppKey(tenantKey, tenantDao);
        if (verify instanceof AppResponseModel) {
            return (AppResponseModel) verify;
        }

        Tenant tenant1;

        if (userDto.getTenantKey() != null) {
            Optional<Tenant> optionalTenant = tenantDao.findDistinctByAppKey(userDto.getTenantKey());
            if (!optionalTenant.isPresent()) {
                appResponseModel.setStatus(false);
                appResponseModel.setMessage("Could not find Tenant using tenant Id passed");
                appResponseModel.setHttpStatus(HttpStatus.BAD_REQUEST);
                return appResponseModel;
            }
            tenant1 = optionalTenant.get();
        } else {
            tenant1 = (Tenant) verify;
        }

        try {
            Optional<User> userNameOrEmail = findByUserNameOrEmail(userDto.getUserName(), userDto.getEmail());

            if ((!userNameOrEmail.isPresent() && userDto.getId() == null) || userDto.getId() != null) {
                User user = userDto.getUser();
                Optional<Role> optionalRole = roleDao.findById(userDto.getRole());
                if (!optionalRole.isPresent()) {
                    appResponseModel.setStatus(false);
                    appResponseModel.setMessage("Role does not exist by specified id!");
                    return appResponseModel;
                }

                Optional<User> optionalUser = Optional.empty();
                if (userDto.getId() != null) {
                    optionalUser = userDao.findById(userDto.getId());
                    if (optionalUser.isPresent())
                        user.setUserType(optionalUser.get().getUserType());
                }

                user.setSuperUser(false);
                if (userDto.getId() != null) {
                    user.setId(userDto.getId());
                }


                user.setRole(optionalRole.get());
                user.setTenant(tenant1);
                user.setOtherName(userDto.getOtherName());

                if (userDto.getId() == null) {
                    user.setEnabled(false);
                }

                if (userDto.getId() == null) {
                    user.setEnabled(false);
                }
                if (userType != null)
                    user.setUserType(new UserType(userType));

                user = userDao.saveAndFlush(user);
//                find or create user account
                Optional<Account> userAccount = accountDao.findDistinctByUserId(user);
                if (!userAccount.isPresent()) {
                    createUserAccount(user, userDto.getUserPermissions(), userDto.getUserName());
                }else{
                    userAccount.get().setPermissions(permissionDao.findAllByNameIn(userDto.getUserPermissions()));
                    accountDao.save(userAccount.get());
                }

                String object = mapper.writeValueAsString(user);
                log.info("created=[{}]", object);

                appResponseModel.setStatus(true);
                appResponseModel.setMessage("User successfully created. The user is disabled and should be enabled");
                appResponseModel.setData(user);
                return appResponseModel;
            } else {
                String existingRole = mapper.writeValueAsString(userNameOrEmail.get());
                log.warn("objectExists=[{}]", existingRole);
                appResponseModel.setStatus(false);
                appResponseModel.setMessage(existingRole);
                return appResponseModel;
            }
        } catch (NonUniqueResultException | JsonProcessingException ex) {
            appResponseModel.setStatus(false);
            appResponseModel.setMessage(ex.getMessage());
            return appResponseModel;
        }


    }

    @Override
    public AppResponseModel enableUser(String username) {
        AppResponseModel appResponseModel = new AppResponseModel();
        if (enabledUser(username) != null) {
            User user = userDao.findTopByUserNameIgnoreCase(username);

            publisher.publishEvent(new NewUserCreatedEvent(this, "password", user, user.getTenant().getName(), user.getTenant().getClientBaseUrl()));
            appResponseModel.setStatus(true);
            appResponseModel.setMessage("Action successful! The user should verify their email to enable authentication");
            appResponseModel.setData(user);
            return appResponseModel;
        } else if (enabledUser(username) == null) {
            appResponseModel.setStatus(false);
            appResponseModel.setMessage("User could not be successfully enabled because user is missing. Contact admin");
            return appResponseModel;
        }
        appResponseModel.setStatus(true);
        appResponseModel.setMessage("User is already enabled!");
        return appResponseModel;
    }

    public boolean findTopByUserNameIgnoreCaseOrEmail(String email, String userName) {
        return userDao.existsByUserNameIgnoreCase(userName) || userDao.existsByEmailIgnoreCase(email);
    }

    public static Optional<User> findUserByUserName(UserDao userDao, String userName) {
        try {
            User user = userDao.findTopByUserNameIgnoreCase(userName);
            return Optional.of(user);
        } catch (EntityNotFoundException | NoResultException | NonUniqueResultException ex) {
            log.error(ERRORLOGFORMAT, ex.getMessage());
        }
        return Optional.empty();
    }


    private boolean userRoleChanged(User user, UserDto userDto) {
        return !userDto.getRole().equals(user.getRole().getId());
    }

    @Override
    public Optional<User> findUserByPhoneNumber(String phoneNumber) {
        try {
            return Optional.ofNullable(userDao.findByPhoneNumberLike(phoneNumber));
        } catch (EntityNotFoundException | NoResultException | NonUniqueResultException ex) {
            log.error(ERRORLOGFORMAT, ex.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public ResponseEntity<AppResponseModel> forgotPassword(String email) {
        Optional<User> optionalUser = userDao.findByEmail(email);
        AppResponseModel appResponseModel = new AppResponseModel();
        if (optionalUser.isPresent()) {
            optionalUser.get().setEnabled(false);
            userDao.save(optionalUser.get());
            appResponseModel.setStatus(true);
            publisher.publishEvent(new UserPasswordResetEvent(optionalUser.get(), optionalUser.get().getTenant().getName(), optionalUser.get().getTenant().getClientBaseUrl()));
            appResponseModel.setMessage("Success! Reset link sent to " + email);
            return ResponseEntity.ok(appResponseModel);
        } else {
            appResponseModel.setStatus(false);
            appResponseModel.setMessage("Could not find a user with specified email");
            return ResponseEntity.ok(appResponseModel);
        }
    }

    @Override
    public ResponseEntity<AppResponseModel> verifyEmailResetPassword(ResetPassword resetPassword) {
        Optional<User> user = userDao.findDistinctByCorrelator(resetPassword.getUserID());
        AppResponseModel appResponseModel = new AppResponseModel();
        if (user.isPresent()) {

            user.get().setEnabled(true);
            User user1 = userDao.save(user.get());

            if (resetUserPassword(user1, resetPassword.getPassword())) {
                appResponseModel.setData(user1);
                appResponseModel.setStatus(true);
                appResponseModel.setMessage("Successfully verified user password!");
            } else {
                appResponseModel.setStatus(false);
                appResponseModel.setMessage("Couldn't find user account!");
            }
        } else {
            appResponseModel.setStatus(false);
            appResponseModel.setMessage("User could not be found");
        }
        return ResponseEntity.ok(appResponseModel);
    }

    @Override
    public void createUserAccount(User u, List<String> permissions, String userName) {
        Account account = new Account();
        account.setBlocked(false);
        account.setFailedAttempts(0);
        account.setUserId(u);
        account.setPassword(encoder.encode(defaultPassword));
        account.setUsername(userName);
        account.setPermissions(permissionDao.findAllByNameIn(permissions));
        account = accountDao.save(account);
    }

    private boolean resetUserPassword(User user, String password) {
        Optional<Account> userAccount = accountDao.findDistinctByUserId(user);
        if (userAccount.isPresent()) {
            userAccount.get().setPassword(encoder.encode(password));
            accountDao.save(userAccount.get());
            return true;
        } else {
            return false;
        }
    }

    public static Object verifyRequestAppKey(String tenantKey, TenantDao tenantDao) {
        AppResponseModel appResponseModel = new AppResponseModel();
        Optional<Tenant> optionalTenant = tenantDao.findDistinctByAppKey(tenantKey);
        if (!optionalTenant.isPresent()) {
            appResponseModel.setStatus(false);
            appResponseModel.setMessage("Request AppKey is invalid!");
            return appResponseModel;
        }
        return optionalTenant.get();
    }
}
