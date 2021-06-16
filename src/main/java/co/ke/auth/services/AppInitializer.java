package co.ke.auth.services;

import co.ke.auth.entities.Permission;
import co.ke.auth.entities.UserType;
import co.ke.auth.repositories.PermissionDao;
import co.ke.auth.repositories.UserTypeDao;
import co.ke.auth.utils.Perm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

        List<String> permissions = Arrays.stream(Perm.class.getDeclaredFields()).map(Field::getName).collect(Collectors.toList());
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
