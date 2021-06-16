package co.ke.auth.services.impl;

import co.ke.auth.entities.Permission;
import co.ke.auth.models.AppResponseModel;
import co.ke.auth.repositories.PermissionDao;
import co.ke.auth.services.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class PermissionServiceImpl extends ResponseInterfaceImpl implements PermissionService {

    @Autowired
    private PermissionDao permissionDao;

    @Override
    public AppResponseModel createRealmPermissions(List<String> permissions) {
            List<Permission> permissions1 = new ArrayList<>();
            for (String s : permissions) {
                if (permissionDao.findByName(s) == null) {
                    permissions1.add(new Permission(s));
                }
            }
            Collection<Permission> permissions2 = permissionDao.saveAll(permissions1);
            return collectionResponse(permissions2);
    }
}
