package co.ke.auth.services.impl;

import co.ke.auth.entities.Role;
import co.ke.auth.entities.Permission;
import co.ke.auth.entities.Tenant;
import co.ke.auth.models.AppResponseModel;
import co.ke.auth.models.RoleDto;
import co.ke.auth.models.RoleModel;
import co.ke.auth.repositories.PermissionDao;
import co.ke.auth.repositories.RoleDao;
import co.ke.auth.repositories.TenantDao;
import co.ke.auth.services.RoleService;
import co.ke.auth.utils.Utilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {
    private static final String ERRORLOG = "error=[{}]";
    Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

    @Autowired
    private TenantDao tenantDao;

    @Autowired
    private PermissionDao permissionDao;

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private RoleDao roleDao;

    @Override
    public AppResponseModel findAllRoles(String tenantAppKey) {
        logger.info("tenantAppKey => {}", tenantAppKey);
        AppResponseModel appResponseModel = new AppResponseModel();
        List<Role> roles = roleDao.findAllByDateTimeCreatedIsNotNullOrderByIdDesc();
        appResponseModel.setStatus(true);

        Optional<Tenant> optionalTenant = tenantDao.findDistinctByAppKey(tenantAppKey);
        if (!optionalTenant.isPresent()) {
            appResponseModel.setStatus(false);
            appResponseModel.setMessage("Request AppKey is invalid!");
            return appResponseModel;
        }

        appResponseModel.setMessage("successfully fetched roles for tenant " + optionalTenant.get().getId().toString());
        appResponseModel.setData(roles.stream().filter(role -> role.getTenant().getId().equals(optionalTenant.get().getId())).collect(Collectors.toList()));
        return appResponseModel;
    }

    @Override
    public AppResponseModel createRole(RoleDto role, String tenantAppKey) {

        AppResponseModel appResponseModel = new AppResponseModel();
        try {
            Role roleRepositoryName = roleDao.findByName(role.getName());
            Role role1;
            Tenant tenant;
            Optional<Tenant> optionalTenant = tenantDao.findDistinctByAppKey(tenantAppKey);

            if (!optionalTenant.isPresent()) {
                appResponseModel.setStatus(false);
                appResponseModel.setMessage("Request AppKey is invalid!");
                return appResponseModel;
            } else {
                tenant = optionalTenant.get();
            }

            if (role.getId() == null) {
                if (roleRepositoryName != null) {
                    appResponseModel.setStatus(false);
                    appResponseModel.setMessage("Role with similar name already Exists");
                    return appResponseModel;
                }
                role1 = role.getRole();
                role1.setTenant(tenant);
                role1.setPermissions(permissionDao.findAllByNameIn(role.getPermissions()));
                appResponseModel.setStatus(true);
                role1 = roleDao.saveAndFlush(role1);
                appResponseModel.setData(role1);
                return appResponseModel;
            } else {
                Optional<Role> optionalRole = roleDao.findById(role.getId());
                if (optionalRole.isPresent()) {
                    role1 = optionalRole.get();
                    role1.setPermissions(permissionDao.findAllByNameIn(role.getPermissions()));
                    role1.setName(role.getName());
                    role1.setTenant(tenant);
                    appResponseModel.setStatus(true);
                    role1 = roleDao.saveAndFlush(role1);
////                    update all users who had this role
//                    publisher.publishEvent(new RoleChangeEvent(this, role1.getTenant().getRealm().getRealmId(), role1));
                    appResponseModel.setData(role1);
                    return appResponseModel;
                } else {
                    appResponseModel.setStatus(false);
                    appResponseModel.setMessage("Role could not be found with specified id");
                    return appResponseModel;
                }
            }
        } catch (PersistenceException ex) {
            logger.error(ERRORLOG, ex.getMessage());
        }
        return appResponseModel;
    }

    @Override
    public AppResponseModel findAllPrivileges(String tenantAppKey) {
        AppResponseModel appResponseModel = new AppResponseModel();
        appResponseModel.setStatus(true);
        appResponseModel.setMessage("successfully fetched privileges");
        appResponseModel.setData(permissionDao.findAll());
        return appResponseModel;
    }

    @Override
    public AppResponseModel findAllRoles(String requestAppKey, Integer roleId) {
        AppResponseModel appResponseModel = new AppResponseModel();
        Optional<Role> optionalUser = roleDao.findById(roleId);
        if (!optionalUser.isPresent()) {
            appResponseModel.setStatus(false);
            appResponseModel.setMessage("Role not found using specified ID");
        } else {
            appResponseModel.setStatus(true);
            appResponseModel.setMessage("Role fetched successfully!");
            List<Role> roles = new ArrayList<>();
            roles.add(optionalUser.get());
            appResponseModel.setData(RoleModel.transform(optionalUser.get(), permissionDao.findAllByRolesIn(roles)));
        }
        return appResponseModel;
    }

    @Override
    public AppResponseModel viewOneRoles(String requestAppKey, Integer userId) {
        AppResponseModel appResponseModel = new AppResponseModel();
        Optional<Role> optionalRole = roleDao.findById(userId);
        if (!optionalRole.isPresent()) {
            appResponseModel.setStatus(false);
            appResponseModel.setMessage("Role not found using specified ID");
        } else {
            appResponseModel.setStatus(true);
            appResponseModel.setMessage("Role fetched successfully!");
            List<Role> roles = new ArrayList<>();
            roles.add(optionalRole.get());
            logger.info("optionalRole {}", Utilities.toJson(optionalRole.get()));
            List<Permission> permissions = permissionDao.findAllByRolesIn(roles);
            logger.info("role permissions {}", Utilities.toJson(permissions));

            appResponseModel.setData(RoleDto.transform(optionalRole.get(), permissions.isEmpty() ? new ArrayList<>() : permissions));
        }
        return appResponseModel;
    }

}
