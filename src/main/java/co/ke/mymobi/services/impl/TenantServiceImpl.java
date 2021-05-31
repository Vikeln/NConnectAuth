package co.ke.mymobi.services.impl;

import co.ke.mymobi.entities.*;
import co.ke.mymobi.models.*;
import co.ke.mymobi.repositories.*;
import co.ke.mymobi.services.TenantService;
import co.ke.mymobi.services.UserService;
import co.ke.mymobi.utils.Utilities;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Optional;

@Service
public class TenantServiceImpl implements TenantService {
    Logger logger = LoggerFactory.getLogger(TenantServiceImpl.class);
    private static final String ERRORLOG = "error=[{}]";
    private ObjectMapper mapper = Utilities.mapper();

    @Autowired
    private PermissionDao permissionDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private UserService userService;

    @Autowired
    private TenantDao tenantDao;


    @Override
    public AppResponseModel findAllTenants() {
        AppResponseModel appResponseModel = new AppResponseModel();
        List<Tenant> tenants = tenantDao.findAll();
        appResponseModel.setStatus(true);
        appResponseModel.setMessage(tenants.isEmpty() ? "No tenants found" : "Tenants fetched!");
        appResponseModel.setData(tenants);
        return appResponseModel;
    }

    @Override
    @SneakyThrows
    public Optional<Object> createUpdateTenant(Tenant tenant, TenantDTo tenantDTo, boolean createUSer) {
        logger.info("tenantDTo {}", Utilities.toJson(tenantDTo));
        try {
            Tenant roleRepositoryName = tenantDao.findDistinctByName(tenant.getName());
            if ((tenant.getId() == null && roleRepositoryName == null) || (tenant.getId() != null && tenant.getName().equals(roleRepositoryName.getName()))) {
                if (tenant.getId() == null) {
                    boolean found = false;
                    while (!found) {
                        String key = Utilities.createAppKey();
                        if (!tenantDao.existsByAppKey(key)) {
                            tenant.setAppKey(key);
                            found = true;
                        }
                    }

                }
                tenant = tenantDao.saveAndFlush(tenant);
                logger.info("tenant {}", Utilities.toJson(tenant));
                boolean createAdminUser = tenantDTo.isCreateAdminUser();
                User user = null;
                if (tenantDTo.getId() == null) {
                    if (createUSer || createAdminUser) {
                        logger.info("should be creating admin user");

                        List<Permission> realmPermissions = permissionDao.findAll();
                        RoleDto role = RoleDto.transform(tenant.getName() + " ADMIN", realmPermissions);

                        Role adminRole = role.getRole();
                        adminRole.setTenant(tenant);
                        adminRole.setPermissions(permissionDao.findAllByNameIn(role.getPermissions()));
                        adminRole = roleDao.saveAndFlush(adminRole);

                        AppResponseModel createdUser = userService.createKeycloakUserCorrelator(tenant.getAppKey(), UserDto.transform(tenantDTo, adminRole.getId(),adminRole.getPermissions()),1);
                        logger.info("createdUser  {}", Utilities.toJson(createdUser));
                        user = (User) createdUser.getData();
                        if (createdUser.isStatus()) {
                            AppResponseModel enableUser = userService.enableUser(tenantDTo.getAdminUserName());
                            logger.info("enableUser  {}", Utilities.toJson(enableUser));
                        }
                    } else {
                        logger.info("tenant doesn't need an admin user");

                    }
                }
                logger.info("createAdminUser  " + (createAdminUser ? "TRUE " : "FALSE "));
                logger.info("createUSer  " + (createUSer ? "TRUE " : "FALSE "));
                logger.info("user  ", Utilities.toJson(user));

                if (createUSer || createAdminUser) {
                    return Optional.of((TenantModel.transform(tenant, user)));
                } else {
                    return Optional.of(tenant);
                }

            } else {
                return Optional.of(roleRepositoryName);
            }
        } catch (PersistenceException ex) {
            logger.error(ERRORLOG, ex.getMessage());
        }
        return Optional.empty();
    }


}
