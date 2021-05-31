package co.ke.mymobi.services;

import co.ke.mymobi.entities.Permission;
import co.ke.mymobi.entities.UserType;
import co.ke.mymobi.repositories.PermissionDao;
import co.ke.mymobi.repositories.UserTypeDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class AppInitializer {
    @Autowired
    private UserTypeDao userTypeDao;
    @Autowired
    private PermissionDao permissionDao;

    @PostConstruct
    public void checkCreateUserTypes() {
        if (!checkAdminType()) {
            log.info("creating ADMIN UserType");
            UserType type = new UserType(1, "ADMIN");
            userTypeDao.save(type);
        }

        if (!checkUserType()) {
            log.info("creating USER UserType");
            UserType type = new UserType(2, "USER");
            userTypeDao.save(type);
        }
        String[] permissions = {"CAN_VIEW_USERS", "CAN_VIEW_USER_DETAILS", "CAN_CREATE_USERS"};
        List<Permission> list = new ArrayList<>();
        for (String perm : permissions) {
            if (permissionDao.findByName(perm) == null)
                list.add(new Permission(perm));
        }
        if (!list.isEmpty())
            permissionDao.saveAll(list);
    }

    private boolean checkUserType() {
        return userTypeDao.existsById(2);
    }

    private boolean checkAdminType() {
        return userTypeDao.existsById(1);
    }
}
